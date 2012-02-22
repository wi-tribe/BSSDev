<?php

/**
 * simple implementation of database functions
 * this class handles the basic CRUD functions on mysql.
 * 
 * version 0.2 revision 2
 *
 * @author waqas.toor@pk.wi-tribe.com
 */



class Database {
    //put your code here

    private $conn ;
    private $record_set ;
    private $count ;


    function __contruct()
    {
        
    }
    /***
     * Function: Connect
     * Description: Connects to the specified database in mysql
     * @params : array containing information about the database
     */

    function connect($default)
    {
         try {
            $this->conn = mysql_connect($default['host_name'], $default['user_name'], $default['password'] , true);
            mysql_select_db($default['database'], $this->conn);
        }
        catch(Exception $e)
        {
            echo $e->getMessage();
        }
    }
    /**
     *  executes external query if the query is more complex
     *
     * @param string $extern_query
     * @return record_set
     *
     *
     */
    function execute_query( $extern_query )
    {
        if($this->conn)
        {
            $this->record_set = mysql_query($extern_query, $this->conn );
            return $this->record_set;
        }
        else
            return null;
    }
    /**
     *
     * @return integer count
     *
     *
     */
    function get_record_count()
    {
        if($this->conn && $this->record_set)
        {
            $this->count = mysql_num_rows($this->record_set);
            return $this->count;
        }
        else
        {
            return null;
        }
    }

    function get_table_record_count( $table_name )
    {
        if( $this->conn )
        {
            $record = mysql_query("SELECT * FROM $table_name" , $this->conn);
            return mysql_num_rows($record);
        }
        else
            return null;
    }


    /**
     *
     * @return integer last insert Id
     *
     */
    function get_last_insert_id()
    {
        if($this->conn )
        {
            $last_insert_id = mysql_insert_id($this->conn);
            return $last_insert_id ;
        }
        else
        {
            return 0;
        }
    }

    
    /**
     *
     *  Possible commands for now
     *  1: order by
     *  2: order by asc
     *  3: order by desc
     *  4: where order by asc
     *  5: where order by desc
     *  6: query seed = sql query that is going to be available in field variable
     *  7: complex query
     *
     *
     * @param string $table_name
     * @param string $command
     * @param string $field
     * @param string $value
     * @return array
     */

    function get_table_data( $table_name , $command = null , $field = null , $value = null )
    {
        $query_seed = "";
		$command = strtolower($command);
        switch($command)
        {
			case 'where':
                $query_seed = "WHERE $field = '$value'";
                break;
            case 'order by':
                $query_seed = "ORDER BY $field";
                break;
            case 'order by asc':
                $query_seed = "ORDER BY $field ASC";
                break;
            case 'order by desc':
                $query_seed = "ORDER BY $field DESC";
                break;
            case 'where order by asc':
                $query_seed = "WHERE $field = '$value' ORDER BY $field ASC";
                break;
            case 'where order by desc':
                $query_seed = "WHERE $field = '$value' ORDER BY $field DESC";
                break;
            case 'query seed':
                $query_seed = $field;
                break;                
            default:
                $query_seed = '';
                break;
        }

        if($this->conn)
        {
            $return_array = array();
            $query = "SELECT * FROM $table_name $query_seed";

            $this->record_set = mysql_query($query , $this->conn);
            if( $this->record_set )
            {
                $counter = mysql_num_rows($this->record_set);
                if( $counter > 0 )
                {
                    for( $i = 0 ; $i < $counter ; $i ++ )
                    {
                        $row = mysql_fetch_assoc($this->record_set);
                        $return_array[] = $row;
                    }
                }
            }
            return $return_array;

        }
     
    }
    function execute_complex_query($query)
    {		
         if($this->conn)
        {
            $return_array = array();
            $this->record_set = mysql_query($query , $this->conn);
			if( $this->record_set )
            {
                $counter = mysql_num_rows($this->record_set);
				if( $counter > 0 )
                {
                    for( $i = 0 ; $i < $counter ; $i ++ )
                    {
                        $row = mysql_fetch_assoc($this->record_set);
                        $return_array[] = $row;
                    }
                }
            }
            return $return_array;

        }
    }
    /**
     *
     * @param string $table_name
     * @param array $data_set
     * @return integer result
     *
     */
   

    function insert_table_data( $table_name , $data_set )
    {
        foreach( $data_set as $key => $value)
        {
             $fields[] =  $key;
             $filtered_value = addslashes( $value );
             $values[] = "'$filtered_value'";
        }

        if( $this->conn )
        {
            $fields_string = implode( ",", $fields );
            $values_string = implode( ",", $values );
            $query = "INSERT INTO $table_name ( $fields_string ) VALUES ( $values_string )";
            $result = mysql_query($query, $this->conn);
            return $result;
        }

    }
    /**
     *
     * @param string $table_name
     * @param array $data_set
     * @param integer $id
     * @return integer result
     *
     */

    function update_table_data( $table_name , $data_set , $field , $field_value )
    {

        if( $this-> conn )
        {
          foreach( $data_set as $key => $value)
             {
                 $filtered_value = addslashes( $value );
                 $query_seed .= "$key = '$filtered_value',";
             }
             $query_seed = rtrim($query_seed , ',');
             
             $update_query = "UPDATE $table_name SET $query_seed WHERE $field = '$field_value'";
             $result = mysql_query($update_query , $this->conn);
             return $result;

        }
            
    }
    /**
     *
     * @param string $table_name
     * @param integer $id
     * @return integer result
     *
     */

    function delete_table_data($table_name , $field , $field_value )
    {
        if($this->conn)
        {
            $query = "DELETE FROM $table_name WHERE $field = '$field_value'";
            $result = mysql_query($query , $this->conn);
            return $result;
        }

    }

    

    function __destruct()
    {
        if($this->conn)
        {
            mysql_close($this->conn);
            return true;
        }
        else
            return false;
    }

}

?>
