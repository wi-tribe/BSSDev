package PHPGACL::Check;

use 5.008003;
use strict;
use warnings;

require Exporter;

our @ISA = qw(Exporter);


# Items to export into callers namespace by default. Note: do not export
# names by default without a very good reason. Use EXPORT_OK instead.
# Do not simply export all your public functions/methods/constants.

# This allows declaration	use PHPGACL::Check ':all';
# If you do not need this, moving things directly into @EXPORT or @EXPORT_OK
# will save memory.
our %EXPORT_TAGS = ( 'all' => [ qw(
	
) ] );

our @EXPORT_OK = qw( acl_check );

our @EXPORT = qw(

);

our $VERSION = '1.00';


# Preloaded methods go here.


my $db_type = "mysql";
my $db_name = "";
my $db_username = "";
my $db_password = "";
my $db_hostname = "";
my $db_table_prefix = "";
my $group_switch = "_group_";

my $aco_section_value;
my $aco_value;
my $aro_section_value;
my $aro_value;
my $axo_section_value;
my $axo_value;
my $root_aro_group_id;
my $root_axo_group_id;

my @aro_group_ids;
my @axo_group_ids;

my $sth;
my $allow;
my @data;

my $debug = "";
#--------------------------------------------------
# 
# 	Function: 	acl_check()
# 	Purpose:	Wrapper that calls acl_query and simply extracts the result
#
#	Input:		
# 
#   Output:		1 for allowed access
#				0 for denied access
#-------------------------------------------------- 

sub acl_check {   # $aco_section_value, $aco_value, $aro_section_value, $aro_value, $axo_section_value=NULL, $axo_value=NULL, $root_aro_group_id=NULL, $root_axo_group_id=NULL, $debug=NULL) {


	my @chk = acl_query( $_[0], $_[1], $_[2], $_[3], $_[4], $_[5], $_[6], $_[7], $_[8] );

	if ($chk[2] eq "TRUE") { 
		return 1;
	} elsif ( $chk[2] eq "FALSE") {
		return 0;
	}
}

#/*======================================================================*\
#	Function:   acl_query()
#	Purpose:	Main function that does the actual ACL lookup.
#					Returns as much information as possible about the ACL so other functions
#					can trim it down and omit unwanted data.
#	Output:		On ALLOW, Outputs an array with these columns:
#			 	@row[0] = 'acl_id' (id of dominant ACL)
#				@row[1] = 'return_value' 
#				@row[2] = 'allow' = "TRUE"
#				On DENY, Outputs an array with these columns
#				@row[0] = ''
#				@row[1] = ''
#				@row[2] = 'FALSE'
#\*======================================================================*/
sub  acl_query {  # $aco_section_value, $aco_value, $aro_section_value, $aro_value, $axo_section_value=NULL, $axo_value=NULL, $root_aro_group_id=NULL, $root_axo_group_id=NULL, $debug=NULL) {

	my $dbh = DBI->connect("DBI:".$db_type.":" . $db_name, $db_username, $db_password);
	my $aco_section_value = shift || die "Missing required argument";
	my $aco_value = shift || die "Missing required argument";
	my $aro_section_value = shift || die "Missing required argument";
	my $aro_value = shift || die "Missing required argument";
	my $axo_section_value = shift || "";
	my $axo_value= shift || "";
	my $root_aro_group_id = shift || "";
	my $root_axo_group_id = shift || "";
	my $debug = shift || "";

	my @retarr;

	my $sql_aro_group_ids;
	my $sql_axo_group_ids;

	my $query;
	my @row;


	# Perform caching here if implemented
	#		/*
	#		 * Grab all groups mapped to this ARO/AXO
	#		 */
	# Function returns array of group ids

	@aro_group_ids = acl_get_groups($aro_section_value, $aro_value, $root_aro_group_id, 'ARO');

	# Is there a better way to test for non-zero length perl arrays?
	if ((@aro_group_ids)) {
		$sql_aro_group_ids = join(",", @aro_group_ids);
		debug_text("sql_aro_group_ids: " . $sql_aro_group_ids . "\n");
	}

	if (($axo_section_value ne '') && ($axo_value ne '')) {
		@axo_group_ids = acl_get_groups($axo_section_value, $axo_value, $root_axo_group_id, 'AXO');
		if (@axo_group_ids) {
			$sql_axo_group_ids = join(",", @axo_group_ids);
			debug_text("sql_axo_group_ids: " . $sql_axo_group_ids . "\n");
		}
	}

	#	/*
	#	 * This query is where all the magic happens.
	#	 * The ordering is very important here, as well very tricky to get correct.
	#	 * Currently there can be  duplicate ACLs, or ones that step on each other toes. In this case, the ACL that was last updated/created
	#	 * is used.
	#	 *
	#	 * This is probably where the most optimizations can be made.
	#	 */

	my @order_by;

	$query = '
		SELECT		a.id,a.allow,a.return_value
		FROM		'. $db_table_prefix .'acl a
		LEFT JOIN 	'. $db_table_prefix .'aco_map ac ON ac.acl_id=a.id';

	if ($aro_section_value ne $group_switch) {
		$query .= '
			LEFT JOIN	'. $db_table_prefix .'aro_map ar ON ar.acl_id=a.id';
	}

	if ($axo_section_value ne $group_switch) {
		$query .= '
			LEFT JOIN	'. $db_table_prefix .'axo_map ax ON ax.acl_id=a.id';
	}

	#		/*
	#		 * if there are no aro groups, don't bother doing the join.
	#		 */
	if ($sql_aro_group_ids) {
		$query .= '
			LEFT JOIN	'. $db_table_prefix .'aro_groups_map arg ON arg.acl_id=a.id
			LEFT JOIN	'. $db_table_prefix .'aro_groups rg ON rg.id=arg.group_id';
	}

	#		// this join is necessary to weed out rules associated with axo groups
	$query .= '
		LEFT JOIN	'. $db_table_prefix .'axo_groups_map axg ON axg.acl_id=a.id';

	#		/*
	#		 * if there are no axo groups, don't bother doing the join.
	#		 * it is only used to rank by the level of the group.
	#		 */
	if ($sql_axo_group_ids) {
		$query .= '
			LEFT JOIN	'. $db_table_prefix .'axo_groups xg ON xg.id=axg.group_id';
	}

	#		//Move the below line to the LEFT JOIN above for PostgreSQL's sake.
	#		//AND	ac.acl_id=a.id
	$query .= '
		WHERE		a.enabled=1
		AND		(ac.section_value='. $dbh->quote($aco_section_value) .' AND ac.value='. $dbh->quote($aco_value) .')';

	#		// if we are querying an aro group
	if ($aro_section_value eq $group_switch) {
	#			// if acl_get_groups did not return an array
		if ( !($sql_aro_group_ids) ) {
			debug_text ('acl_query(): Invalid ARO Group: '. $aro_value);
			return "FALSE";
		}

		$query .= '
			AND		rg.id IN ('. $sql_aro_group_ids .')';

		push(@order_by, '(rg.rgt-rg.lft) ASC');
	} else {
		$query .= '
			AND		((ar.section_value='. $dbh->quote($aro_section_value) .' AND ar.value='. $dbh->quote($aro_value) .')';

		if ( ($sql_aro_group_ids) ) {
			$query .= ' OR rg.id IN ('. $sql_aro_group_ids .')';

			push(@order_by, '(ar.value IS NOT NULL) DESC');
			push(@order_by, '(rg.rgt-rg.lft) ASC');
		}

		$query .= ')';
	}


	#		// if we are querying an axo group
	if ($axo_section_value eq $group_switch) {
	#			// if acl_get_groups did not return an array
		if ( !($sql_axo_group_ids) ) {
			debug_text ('acl_query(): Invalid AXO Group: '. $axo_value);
			return "FALSE";
		}

		$query .= '
			AND		xg.id IN ('. $sql_axo_group_ids .')';

		push(@order_by, '(xg.rgt-xg.lft) ASC');
	} else {
		$query .= '
			AND		(';

		if (($axo_section_value eq '') && ($axo_value eq '')) {
			$query .= '(ax.section_value IS NULL AND ax.value IS NULL)';
		} else {
			$query .= '(ax.section_value='. $dbh->quote($axo_section_value) .' AND ax.value='. $dbh->quote($axo_value) .')';
		}

		if (($sql_axo_group_ids)) {
			$query .= ' OR xg.id IN ('. $sql_axo_group_ids .')';

			push(@order_by, '(ax.value IS NOT NULL) DESC');
			push(@order_by, '(xg.rgt-xg.lft) ASC');
		} else {
			$query .= ' AND axg.group_id IS NULL';
		}

		$query .= ')';
	}

	#		/*
	#		 * The ordering is always very tricky and makes all the difference in the world.
	#		 * Order (ar.value IS NOT NULL) DESC should put ACLs given to specific AROs
	#		 * ahead of any ACLs given to groups. This works well for exceptions to groups.
	#		 */

	push(@order_by, 'a.updated_date DESC');

	$query .= '
		ORDER BY	'. join(',', @order_by) . '
		';

	#		// we are only interested in the first row
	# Not sure of the Perl::DBI method of portable LIMIT statement generation, so just using the 
	#  non-portable MySQL LIMIT syntax.
	$query .= '
		LIMIT 1
		';
	$sth = $dbh->prepare($query)
		or die "Couldn't prepare query: " . $sth->errstr;

	$sth->execute()
		or die "Couldn't execute query: " . $query . " Errstr: " . $sth->errstr;


	if (!($sth)) {
		debug_db('acl_query');
		return "FALSE";
	}

	@row = $sth->fetchrow_array();

	#		/*
	#		 * Return ACL ID. This is the key to "hooking" extras like pricing assigned to ACLs etc... Very useful.
	#		 */
	if ((@row)) {
	#			// Permission granted?
		if ( ($row[1]) && ($row[1] == 1) ) {
			$allow = 'TRUE';
		} else {
			$allow = 'FALSE';
		}

	# We have an option here of implementing the return code as a perl hash or as a 
	#  number-indexed array. Perl doesn't have these key-value arrays that can be accessed by
	#  index, AFAIK.
	#		$retarr = array('acl_id' => &$row[0], 'return_value' => &$row[2], 'allow' => $allow);
		@retarr = ( $row[0], $row[2], $allow);
	} else {
	#			// Permission denied.
		@retarr = ( '', '', 'FALSE');
	}

	#		/*
	#		 * Return the query that we ran if in debug mode.
	#		 */
	debug_text("Query: " . $query . "\n");


	#  Caching not implemented
	#    Insert cache update here.


	debug_text("<b>acl_query():</b> ACO Section: $aco_section_value ACO Value: $aco_value ARO Section: $aro_section_value ARO Value $aro_value ACL ID: ". $retarr[0] .' Result: '. $retarr[2]);

	$sth->finish();

	$dbh->disconnect();
	
	return @retarr;
}




#/*======================================================================*\
#	Function:   acl_get_groups()
#	Purpose:	Grabs all groups mapped to an ARO. You can also specify a root_group_id for subtree'ing.
#\*======================================================================*/
sub acl_get_groups { # ( $section_value, $value, $root_group_id, $group_type) {

	my $dbh = DBI->connect("DBI:".$db_type.":".$db_name, $db_username, $db_password);
	my $section_value = shift || die "Missing required argument";
	my $value = shift || die "Missing required argument";
	my $root_group_id = shift || "";
	my $group_type = shift || "ARO";
	my @retarr;

	my $object_table;
	my $group_table;
	my $group_map_table;
	my $query;
	my $where;

#	switch (lc($group_type)) {
#	switch(strtolower($group_type)) {
	if('axo' eq lc($group_type) ) {
			$group_type = 'axo';
			$object_table = $db_table_prefix . 'axo';
			$group_table = $db_table_prefix . 'axo_groups';
			$group_map_table = $db_table_prefix . 'groups_axo_map';
	} else {
			$group_type = 'aro';
			$object_table = $db_table_prefix . 'aro';
			$group_table = $db_table_prefix . 'aro_groups';
			$group_map_table = $db_table_prefix . 'groups_aro_map';
	}

#	//$profiler->startTimer( "acl_get_groups()");

#	//Generate unique cache id.
#	$cache_id = 'acl_get_groups_'.$section_value.'-'.$value.'-'.$root_group_id.'-'.$group_type;

#	$retarr = $this->get_cache($cache_id);

#	if (!$retarr) {

#		// Make sure we get the groups
		$query = '
				SELECT 		DISTINCT g2.id';
		
		if ($section_value eq $group_switch) {
			$query .= '
				FROM		' . $group_table . ' g1,' . $group_table . ' g2';
			
			$where = '
				WHERE		g1.id=' . $value;
		} else {
			$query .= '
				FROM		'. $object_table .' o,'. $group_map_table .' gm,'. $group_table .' g1,'. $group_table .' g2';
			
			$where = '
				WHERE		(o.section_value='. $dbh->quote($section_value) .' AND o.value='. $dbh->quote($value) .')
					AND		gm.'. $group_type .'_id=o.id
					AND		g1.id=gm.group_id';
		}

#		/*
#		 * If root_group_id is specified, we have to narrow this query down
#		 * to just groups deeper in the tree then what is specified.
#		 * This essentially creates a virtual "subtree" and ignores all outside groups.
#		 * Useful for sites like sourceforge where you may seperate groups by "project".
#		 */
		if ($root_group_id) {
#			//It is important to note the below line modifies the tables being selected.
#			//This is the reason for the WHERE variable.
			$query .= ','. $group_table .' g3';
			
			$where .= '
					AND		g3.id='. $root_group_id .'
					AND		((g2.lft BETWEEN g3.lft AND g1.lft) AND (g2.rgt BETWEEN g1.rgt AND g3.rgt))';
		} else {
			$where .= '
					AND		(g2.lft <= g1.lft AND g2.rgt >= g1.rgt)';
		}
		
		$query .= $where;
		
#		// debug_text($query);
		my $sth = $dbh->prepare($query)
				or die "Couldn't prepare statement: " . $sth->errstr;

		$sth->execute()
			or die "Couldn't execute query: " . $query . " error: ". $sth->errstr;

		my $i = 0;
		
#		//Unbuffered query?
		while (@data = $sth->fetchrow_array()) {
			$retarr[$i++] = $data[0];
		}
		$sth->finish();

#		//Cache data.
#		$this->put_cache($retarr, $cache_id);
#	}

	$dbh->disconnect();

	return @retarr;
}

sub debug_db { # ($string)
	if( $debug eq "TRUE" ) { print shift(@_); }
}

sub debug_text { # ($string)
	if( $debug eq "TRUE" ) { print shift(@_); }
}



1;
__END__
# Below is stub documentation for your module. You'd better edit it!

=head1 NAME

  PHPGACL::Check - Perl Module for Querying phpGACL-created ACL's

=head1 SYNOPSIS

  use PHPGACL::Check qw(acl_check);
  acl_check("US-Articles","ResearchDesk","Employees", "jsmith@dfafunds.com");

=head1 DESCRIPTION

  Provides Perl connectivity to ACL's created by Mike Benoit's 
  phpGACL implementation. 

  Requires editing of the database-related variables specific
  to your installation.

  Simply use the method acl_check() to check an ACL with the same
  syntax as in phpGACL. Returns 1 for ALLOW, 0 for DENY.

=head2 Methods

=over 4

=item * (int) acl_check("aco_section","aco_value","aro_section", "aro_value", 
		["axo_section", "axo_value", "aro_root_group_id", "axo_root_group_id", 
		"debug"]);

	Returns 1 for ALLOW Access to this resource. 
	Returns 0 for DENY Access to this resource.

=back


=head2 EXPORT


=head1 SEE ALSO

http://phpgacl.sourceforge.net

=head1 AUTHOR

Gordon Luk (gluk@dfafunds.com)
phpGACL - Mike Benoit (ipso@snappymail.ca)


=head1 COPYRIGHT AND LICENSE

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 For questions, help, comments, discussion, etc., please join the
 phpGACL mailing list. http://sourceforge.net/mail/?group_id=57103


=cut
