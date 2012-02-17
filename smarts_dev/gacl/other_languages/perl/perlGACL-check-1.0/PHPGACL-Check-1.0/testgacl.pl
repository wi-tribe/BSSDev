#!/usr/local/bin/perl
#--------------------------------------------------
# 
# Test Suite for Perl Connector for phpGACL
# Loops through sets of ACO's and tests against various ARO's.
# 
#-------------------------------------------------- 
use strict;
use DBI;
#require "gacl.class.pm";
use PHPGACL::Check qw(acl_check);

my $dbh;
my $db_type = "";
my $db_name = "";
my $db_user = "";
my $db_password = "";
my $db_table_prefix = "";

my @acl_set;
my $test_sth;
my $test_sth2;
my $result;

$dbh = DBI->connect("DBI:$db_type:$db_name", "$db_user", "$db_password");

$test_sth = $dbh->prepare("SELECT	a.value,
								a.name,
								b.value,
								b.name,

								c.value,
								c.name,
								d.value,
								d.name
					from 	".$db_table_prefix."aco_sections as a
						LEFT JOIN ".$db_table_prefix."aco as b ON a.value=b.section_value,
						".$db_table_prefix."aro_sections as c
						LEFT JOIN ".$db_table_prefix."aro as d ON c.value=d.section_value
					order by a.value, b.value, c.value, d.value")
	or die "Couldn't prepare query: " . $test_sth->errstr;

$test_sth->execute()
	or die "Couldn't execute query: " . $test_sth->errstr;
print "#--------------------------------------------------\n";
print "#\n";
print "# ACO, ARO Test \n";
print "#\n";
print "#--------------------------------------------------\n";
while ( @acl_set = $test_sth->fetchrow_array()) {
	test( @acl_set[0], @acl_set[2], @acl_set[4], @acl_set[6] );
}

$test_sth2 = $dbh->prepare("SELECT	a.value,
								a.name,
								b.value,
								b.name,

								c.value,
								c.name,
								d.value,
								d.name,
								e.value,
								e.name,
								f.value,
								f.name
					from 	".$db_table_prefix."aco_sections as a
						LEFT JOIN ".$db_table_prefix."aco as b ON a.value=b.section_value,
						".$db_table_prefix."aro_sections as c
						LEFT JOIN ".$db_table_prefix."aro as d ON c.value=d.section_value,
						".$db_table_prefix."axo_sections as e
						LEFT JOIN ".$db_table_prefix."axo as f ON e.value=f.section_value
					order by a.value, b.value, c.value, d.value, e.value, f.value")
	or die "Couldn't prepare query: " . $test_sth2->errstr;


$test_sth2->execute()
	or die "Couldn't execute query: " . $test_sth2->errstr;

print "#--------------------------------------------------\n";
print "#\n";
print "# ACO, ARO, AXO Test \n";
print "#\n";
print "#--------------------------------------------------\n";

while ( @acl_set = $test_sth2->fetchrow_array()) {
	test_axos( @acl_set[0], @acl_set[2], @acl_set[4], @acl_set[6], @acl_set[8], @acl_set[10] );
}


$dbh->disconnect();


sub test() {

	
	my $aco_section = $_[0];
	my $aco_value = $_[1];
	my $aro_section = $_[2];
	my $aro_value = $_[3];

	my $result;


	$result =  acl_check($aco_section, $aco_value, $aro_section, $aro_value);
	if ( $result ) {
		print $aco_section . "->" . $aco_value . ", " . $aro_section . "->" . $aro_value . ": Allowed ***********************\n";
	} else {
		print $aco_section . "->" . $aco_value . ", " . $aro_section . "->" . $aro_value . ": Not Allowed\n";
	}
	

}

sub test_axos() {

	my $aco_section = $_[0];
	my $aco_value = $_[1];
	my $aro_section = $_[2];
	my $aro_value = $_[3];
	my $axo_section = $_[4];
	my $axo_value = $_[5];


	$result =  acl_check($aco_section, $aco_value, $aro_section, $aro_value, $axo_section, $axo_value);
	if ( $result ) {
		print $aco_section . "->" . $aco_value . ", " . $aro_section . "->" . $aro_value . ", ".$axo_section."->".$axo_value.": Allowed ************************************************\n";
	} else {
		print $aco_section . "->" . $aco_value . ", " . $aro_section . "->" . $aro_value . ", ".$axo_section."->".$axo_value. ": Not Allowed \n";
	}
	
}
