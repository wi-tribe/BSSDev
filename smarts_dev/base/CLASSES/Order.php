<?php

define( ORDER_REQUEST,				1);
define( ORDER_PARTIAL_ASSIGN,       2);
define( ORDER_ASSIGN,				3);
define( ORDER_PARTIAL_RECIEVE,      4);
define( ORDER_RECIEVE,				5);
define( ORDER_CANCEL,				6);
define( ORDER_REJECT,				7);
define( REJECTED_ORDER_RECEIVED,		41);

define( ORDER_TYPE_REQUEST,			1);
define( ORDER_TYPE_RETURN,			2);

include_once(CLASSDIR."/Tracker.php");
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/BRMIntegration.php");
include_once(CLASSDIR."/Replacement.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Discount.php");
include_once(CLASSDIR."/Invoice.php");


Class Order 
{
	public $error;
	
	public function __construct()
	{
            $this->db=new DBAccess();
            $this->conf=new config();
            $this->mlog=new Logging();
	}
	
	public function getOrder($order_id)
	{
		$query = " Select * from ".ORDER_T
				." Inner Join ".ORDER_TYPE_T." ON ".ORDER_T.".order_type_id = ".ORDER_TYPE_T.".order_type_id"
				." Inner Join ".USER_T." ON ".USER_T.".user_id = ".ORDER_T.".order_by"
				." Inner Join ".CHANNEL_T." ON ".CHANNEL_T.".channel_id = ".ORDER_T.".order_from_channel"
				." Inner Join (	Select order_id, order_status_id, DECODE(order_status_id,1,'Requested',2,'Partially Processed',3,'Processed',4,'Partially Received',5,'Received',6,'Cancelled',7,'Rejected',41,'Rejected Received') status
									FROM ".ORDER_STATE_T."
									WHERE order_state_id in ( select max(order_state_id) from ".ORDER_STATE_T." GROUP BY order_id)

								) order_state on order_state.order_id = ".ORDER_T.".order_id
				  Where vims_order.order_id = $order_id";
				  
		if(($order = $GLOBALS['_DB']->CustomQuery($query))!=null)
		{	
			return $order;
		}
                $this->error = "Unable to retrieve order information";
		return false;
	}
	
	public function getCardPrices()
	{
		$query = "select DISTINCT voucher_denomination from ".ITEM_INFO_T;
		$cardPrices = $GLOBALS['_DB']->CustomQuery($query);
		
		return $cardPrices;
	}
	
	private function setOrderStatus($order_id, $status,$action_by=null)
	{
            if($action_by==null)
            {
                $action_by = $_SESSION['user_id'];
            }
		
		$insert = "order_id, order_status_id,action_by, action_date";
                $vals = "$order_id,$status,$action_by,CURRENT_DATE";
		
		if(($result=$GLOBALS['_DB']->InsertRecord(ORDER_STATE_T, $insert, $vals))==null)
		{
		//	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\n: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
			$this->error.="Failed to update order status";
			return false;
		}
		return true;
	}
	
	private function AssignItemsToOrder($order_id, $voucher_ids,$action_by=null)
	{
                if($action_by==null)
                {
                    $action_by = $_SESSION['user_id'];
                }
		
		$insert = "order_id, voucher_id";
		//TODO -  make a single call for all the insertions
		
		foreach($voucher_ids as $voucher_id)
		{
			$vals = "$order_id,$voucher_id";
			if(($result=$GLOBALS['_DB']->InsertRecord(ORDER_ITEM_T, $insert, $vals))==null)
			{
				
				$this->error.="Failed to update order status";
				return false;
			}
		}
		return true;
	}
		
	public function orderInventory($order_type_id, $order_by, $order_denomination, $total_items, $order_desc)
	{
		$order_type_id = ORDER_TYPE_REQUEST;
		if(!$this->preProcessRequest($order_by, $order_denomination, $total_items, $order_desc))
		{
                    return false;
                }
		$user_info = $GLOBALS['_USER']->getUserInfo($order_by);
		$order_from_channel = $user_info[0]['user_channel_id'];
		
		$total_price = $order_denomination* $total_items;
		$insert = "order_type_id, order_date, order_by, order_from_channel, total_price, total_items,order_desc,order_denomination";
                $vals = "$order_type_id, CURRENT_DATE, $order_by, $order_from_channel, $total_price, $total_items, '".$order_desc."', $order_denomination";
		
		if(($order_id=$GLOBALS['_DB']->InsertRecord(ORDER_T, $insert, $vals))==null)
		{
		//	var_dump($id);
		//	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\n: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
			$this->error.="Failed Insertion: $arr<br>";
			return false;
		}
		
		$this->setOrderStatus( $order_id,ORDER_REQUEST);
		
		//print_r($result);
		return true;
	}

	public function preProcessRequest($order_by, $order_denomination, $total_items, $order_desc)
	{
		$this->error=null;
		if($order_denomination == '' || $order_denomination == null)
		{
			$this->error.="Please select voucher denomination.<BR>";
		}
		if($total_items == '' || $total_items == null)
		{ 
			$this->error.="Please select voucher quantity.<BR>";
		}
		 
		if($this->error == null)
		{
			return true;
		}
		return false;
	}
	
	public function processOrder($order_id, $voucher_serial_start,$voucher_serial_end)
	{
                
		if(!$this->preProcessOrder($order_id, $voucher_serial_start,$voucher_serial_end) )
		{
                  return false;
                }
           
		$order_details = $this->getOrder($order_id);
             
		if(!$order_details )
		{
			return false;
		}
		$order_details = $order_details[0];
		
		$voucher_obj = new Voucher();
		$tracker_obj = new Tracker();

		$voucher_serials = $voucher_obj->validateVoucherOwner($_SESSION['user_id'],$_SESSION['channel_id'], $voucher_serial_start,$voucher_serial_end);
               
		if(!$voucher_serials)
		{
			$this->error = $voucher_obj->LastMsg;
			return false;
		}

		if(count($voucher_serials)!= $order_details['total_items'])
		{
			$this->error = "Please assign ".$order_details['total_items']." vouchers instead of ".count($voucher_serials)." to process this order";
			return false;
		}
               
		$denom_start=$voucher_obj->getVoucherDenomination($voucher_serials[0]);
                
		$denom_end=$voucher_obj->getVoucherDenomination($voucher_serials[count($voucher_serials)-1]);
                
		if($denom_start != $order_details['order_denomination']|| $denom_end != $order_details['order_denomination'])
		{
			$this->error = "Please assign vouchers of price ".$order_details['order_denomination']." PKR";
			return false;
		}
		$GLOBALS['_DB']->DBlink->StartTrans();
		$this->AssignItemsToOrder($order_id,$voucher_serials);
		$to_channel_id = $order_details['order_from_channel'];
		$to_user = $order_details['order_by'];
		//TODO set vouchers as unusable while moving...
		$tracker_obj->moveInventory($_SESSION['channel_id'],$to_channel_id,$to_user,$voucher_serials,2);
		$this->setOrderStatus( $order_id,ORDER_ASSIGN);
		if($GLOBALS['_DB']->DBlink->HasFailedTrans())
		{
			$GLOBALS['_DB']->DBlink->CompleteTrans();
			return false;
		}
		$GLOBALS['_DB']->DBlink->CompleteTrans();

		return true;
	}

	public function preprocessOrder($order_id, $voucher_serial_start,$voucher_serial_end)
	{
		$this->error =null;
		if( $voucher_serial_start == null	|| $voucher_serial_start==''	)
		{
			$this->error.="Please Enter voucher serial number start<BR>";
		}
		if( $voucher_serial_end	== null		|| $voucher_serial_end==''	)
		{
			$this->error.="Please Enter voucher serial number end.<BR>";
		}

		if($this->error == null)
		{
			return true;
		}

		return false;
	}
	
	public function ReturnInventoryOrder($order_by,$order_denomination, $voucher_serials, $order_desc)
	{
		$order_type_id = ORDER_TYPE_RETURN;
		if(!$this->preProcessReturn($order_by,$order_denomination, $voucher_serials, $order_desc ))
		{
                    return false;
                }
		 
		$user_info = $GLOBALS['_USER']->getUserInfo($order_by);
		$order_from_channel = $user_info[0]['user_channel_id'];
		$total_items = count($voucher_serials);
		
		$voucher_obj = new Voucher();
		$tracker_obj = new Tracker();

		$voucher_ids = $voucher_obj->validateSerialOwner($order_by, $order_from_channel, $voucher_serials);
		if(!$voucher_ids)
   		{
   			$this->error=$voucher_obj->LastMsg;
   			return false;
   		}
		
		$total_price = $order_denomination * $total_items;
		$insert = "order_type_id, order_date, order_by, order_from_channel, total_price, total_items,order_desc,order_denomination";
                $vals = "$order_type_id, CURRENT_DATE, $order_by, $order_from_channel, $total_price, $total_items, '".$order_desc."', $order_denomination";
		
		if(($order_id=$GLOBALS['_DB']->InsertRecord(ORDER_T, $insert, $vals))==null)
		{
		//	var_dump($id);
			$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\n: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
			$this->error.="Failed Insertion: $arr<br>";
			return false;
		}
		
		$this->AssignItemsToOrder($order_id,$voucher_ids);
               
		$parent_user_info = $GLOBALS['_USER']->getUserInfo($user_info[0]['parent_user_id']);
		$to_parent_channel = $parent_user_info[0]['user_channel_id'];
		$to_parent_user = $parent_user_info[0]['user_id'];
			
		$tracker_obj->moveInventory($order_from_channel,$to_parent_channel,$to_parent_user,$voucher_ids,2);
		$this->setOrderStatus( $order_id,ORDER_REQUEST);
		
		return true;
	}
	
	public function preProcessReturn($order_by,$order_denomination, $voucher_serials, $order_desc )
	{
	
		$this->error =null;
		if( count($voucher_serials)<=0)
		{
			$this->error.="Please select atleast one voucher";
		}

		if($this->error == null)
		{	
			return true;
		}
		return false;
	}
		
	public function cancelOrder($order_id)
	{
		$order_details = $this->getOrder($order_id);
		if(!$order_details )
		{
			return false;
		}
		
		if($_SESSION['channel_id'] != $order_details[0]['order_from_channel'])
		{
			$this->error = 'can\'t cancel Order';
			return false;
		}
		
		$this->setOrderStatus( $order_id,ORDER_CANCEL);
		return true;
	}
	
	public function rejectOrder($order_id)
	{
		$order_details = $this->getOrder($order_id);
		if(!$order_details )
		{
			return false;
		}
		//PRINT $_SESSION['channel_id']."=". $order_details[0]['parent_channel_id'];
		if($_SESSION['channel_id'] != $order_details[0]['parent_channel_id'])
		{
			$this->error = 'can\'t cancel Order';
			return false;
		}
		
		$this->setOrderStatus( $order_id,ORDER_REJECT);
		return true;
	}
	
	public function ConfirmOrderReciept($order_id, $voucher_serial_start,$voucher_serial_end,$action_by=null)
	{
		if($action_by==null)
                {
                    $action_by = $_SESSION['user_id'];
                }

                $order_details = $this->getOrder($order_id);
		if(!$order_details )
		{
                        $this->error ="Order details not found!!!";
			return false;
		}
                
                if($order_details[0]['order_status_id']==ORDER_REJECT)
                {
                    $this->setOrderStatus($order_id,REJECTED_ORDER_RECEIVED,$action_by);
                }
                else
                {
                    $this->setOrderStatus($order_id,ORDER_RECIEVE,$action_by);
                }
		$query = "update vims_tracker set transfer_status=1 
		where voucher_id in (select vims_tracker.voucher_id from vims_order_items
					inner join  
					vims_tracker on vims_order_items.voucher_id = vims_tracker.voucher_id
					where end_date is null
					and order_id=$order_id) and end_date is null
				";
		if($GLOBALS['_DB']->CustomModify($query))
		 {
			return true;
		 }
                 else
                 {
                     $this->LastMsg.="Failed to transfer vouchers";
                      return false;
                 }
	}
	
	public function getOrdersToChannel($channel_id,$user_id, $statusArray=null)
	{
		$condition="";
		if($statusArray!=null)
		{	$order_states="";
			foreach($statusArray as $status)
			{
				$order_states.= "$status,";
			}
			$order_states=trim($order_states,",");
			
			$condition = " and order_status_id in ($order_states)";
		}
		$query =	"select ".ORDER_T.".ORDER_ID,
							ORDER_DATE,
							FIRST_NAME as ORDER_BY,
							TOTAL_ITEMS,
							ORDER_DENOMINATION,
							TOTAL_PRICE,
							ORDER_DESC,
							order_status_id,
                                                        decode(order_status_id,1,'Requested',3,'Assigned',5,'Recieved',6,'Cancelled',7,'Rejected',41,'Rejected Received') order_status,
                                                        decode(order_type_id,1,'Inventory Request',2,'Inventory Return') order_type,
                                                        order_type_id,
                                                        last_updated_by
					 from ".ORDER_T."
					 Inner Join (	Select order_id, order_status_id,action_by as last_updated_by
									FROM ".ORDER_STATE_T."
									WHERE order_state_id in ( select max(order_state_id) from ".ORDER_STATE_T." GROUP BY order_id)
									
								) order_state on order_state.order_id = ".ORDER_T.".order_id
					 inner join ".USER_T." on ".USER_T.".user_id = ".ORDER_T.".order_by
					 inner join ".CHANNEL_T." ct on ct.channel_id=".USER_T.".user_channel_id
					 where parent_channel_id=$channel_id and parent_user_id = $user_id $condition";
                     
		if(($data=$GLOBALS['_DB']->CustomQuery($query))!=null)
		 {
			return $data;
		 }

		 $this->LastMsg.="Query failed, cannot get info";
		 return false;
	
	}

	public function getOrdersByChannel($channel_id, $statusArray=null)
	{
		$condition="";
		if($statusArray!=null)
		{	$order_states="";
			foreach($statusArray as $status)
			{
				$order_states.= "$status,";
			}
			$order_states=trim($order_states,",");

			$condition = "and order_status_id in ($order_states)";
		}
		$query =	"select ".ORDER_T.".ORDER_ID,
							ORDER_DATE,
							FIRST_NAME as ORDER_BY,
							TOTAL_ITEMS,
							ORDER_DENOMINATION,
							TOTAL_PRICE,
							ORDER_DESC,
							order_status_id
					 from ".ORDER_T."
					 Inner Join (	Select order_id, order_status_id
									FROM ".ORDER_STATE_T."
									WHERE order_state_id in ( select max(order_state_id) from ".ORDER_STATE_T." GROUP BY order_id)

								) order_state on order_state.order_id = ".ORDER_T.".order_id
					 inner join ".USER_T." on ".USER_T.".user_id = ".ORDER_T.".order_by
					 where order_by = ".$_SESSION['user_id'] ."
					  and order_from_channel = $channel_id
					  $condition
					 ";
                
		if(($data=$GLOBALS['_DB']->CustomQuery($query))!=null)
		 {
			return $data;
		 }

		 $this->LastMsg.="Query failed, cannot get info";
		 return false;
	}
	
	public function getOrderVouchers($order_id) 
	{
		
		$query = " SELECT * from ".ORDER_ITEM_T
				." inner join ".ITEM_INFO_T." ON ".ITEM_INFO_T.".voucher_id = ".ORDER_ITEM_T.".voucher_id"
				." WHERE order_id=$order_id order by voucher_serial asc ";
		//print $query;
		if(($data=$GLOBALS['_DB']->CustomQuery($query))!=null)
		 {
			return $data;
		 }

		 $this->LastMsg.="Query failed, cannot get info";
		 return false;
	}

	public function getOrderstatusdesc($order_status_id)
	{
            $query = " SELECT * from ".ORDER_STATUS_T." 
                        where order_status_id = $order_status_id";
		
		if(($data=$GLOBALS['_DB']->CustomQuery($query))!=null)
		 {
			return $data;
		 }
		 $this->LastMsg.="Query failed, cannot get info";
		 return false;
        }

        // ******************************** Retailer Related Functions Defined Here ************************** //
        public function orderInventorytByRetailer($order_type_id, $order_by, $order_denomination, $total_items, $order_desc)
	{
		$order_type_id = ORDER_TYPE_REQUEST;
		if(!$this->preProcessRequest($order_by, $order_denomination, $total_items, $order_desc))
		{
                    return false;
                }
		$user_info = $GLOBALS['_USER']->getUserInfo($order_by);
		$order_from_channel = $user_info[0]['user_channel_id'];

		$total_price = $order_denomination* $total_items;
		$insert = "order_type_id, order_date, order_by, order_from_channel, total_price, total_items,order_desc,order_denomination";
                $vals = "$order_type_id, CURRENT_DATE, $order_by, $order_from_channel, $total_price, $total_items, '".$order_desc."', $order_denomination";

		if(($order_id=$GLOBALS['_DB']->InsertRecord(ORDER_T, $insert, $vals))==null)
		{
		//	var_dump($id);
		//	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\n: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
			$this->error.="Failed Insertion: $arr<br>";
			return false;
		}

		$this->setOrderStatus( $order_id,ORDER_REQUEST,$order_by);

		//print_r($result);
		return $order_id;
	}

    public function processRetailerOrder($order_id, $voucher_serials,$from_channel_id,$to_channel_id,$from_user,$to_user)
    {
        $voucher = new Voucher();
        $tracker_obj = new Tracker();
        
        $denom_start = $voucher->getVoucherDenomination($voucher_serials[0]);
	$denom_end=$voucher->getVoucherDenomination($voucher_serials[count($voucher_serials)-1]);

        $order_details =  $this->getOrder($order_id);
        $order_details = $order_details[0];

		if($denom_start != $order_details['order_denomination']|| $denom_end != $order_details['order_denomination'])
		{
			$this->error = "Please assign vouchers of price ".$order_details['order_denomination']." PKR";
			return false;
		}
		$GLOBALS['_DB']->DBlink->StartTrans();
		$this->AssignItemsToOrder($order_details['order_id'],$voucher_serials,$from_user);

		$tracker_obj->moveInventoryToRetailer($from_channel_id,$from_user,$to_channel_id,$to_user,$voucher_serials,2);
		$this->setOrderStatus( $order_id,ORDER_ASSIGN,$from_user);
		if($GLOBALS['_DB']->DBlink->HasFailedTrans())
		{
			$GLOBALS['_DB']->DBlink->CompleteTrans();
			return false;
		}
		$GLOBALS['_DB']->DBlink->CompleteTrans();

		return true;
    }

    public function ReturnTransitInventoryOrder($order_id,$order_by,$voucherIDs,$order_denomination,$total_items)
    {
		if(!$this->preProcessReturn($order_by,$order_denomination, $voucherIDs, 'Inventory Reject' ))
		{
                    return false;
                }
                
		$user_info = $GLOBALS['_USER']->getUserInfo($order_by);
		$order_from_channel = $user_info[0]['user_channel_id'];

		$parent_user_info = $GLOBALS['_USER']->getUserInfo($user_info[0]['parent_user_id']);
		$to_parent_channel = $parent_user_info[0]['user_channel_id'];
		$to_parent_user = $parent_user_info[0]['user_id'];

                $tname="VIMSCLPREVR_".time();
   		$query="select count(*) as t from tab where tname='$tname'";
   		$data=$this->db->CustomQuery($query);

   		if($data[0]['t']>=1)
   		{
   			$this->db->DBlink->Execute("truncate table $tname");#drop temp table
   			$this->db->DBlink->Execute("drop table $tname");#drop temp table
   		}

   		//create a temp table
   		$table="create global temporary table $tname
				(
				  voucher_id number
				)
				on commit preserve rows";

		$this->db->DBlink->Execute($table);

   		//create insert array
   		for($i=0; $i< sizeof($voucherIDs); $i++)
   			$this->db->DBlink->Execute("insert into $tname (voucher_id) values (".$voucherIDs[$i].")");

               
                //Validate the VoucherID's In Transit
                $query = "select count(*)as Vouchercount from vims_tracker where voucher_id in (select voucher_id from $tname )
                                    and assigned_to=$order_by and transfer_status=".INVENTORY_IN_TRANSIT." and action_by=$to_parent_user
                                    and from_channel_id = $to_parent_channel  and to_channel_id =$order_from_channel and end_date is null";
                
               $transitVouchCount = $GLOBALS['_DB']->CustomQuery($query);
               
               if($transitVouchCount[0]['vouchercount']!=$total_items)
               {
                   if($transitVouchCount[0]['vouchercount']<=0)
                        $this->error = "No vouchers found in Transit!";
                   else
                        $this->error = "Not all vouchers assigned in transit state";
                  return false;
               }
               
		$voucher_obj = new Voucher();
		$tracker_obj = new Tracker();
                
                $GLOBALS['_DB']->DBlink->StartTrans();

                $tracker_obj->moveTransitInventoryToParentChannel($order_by, $order_from_channel, $to_parent_channel, $voucherIDs, $tname,$to_parent_user);
                $this->setOrderStatus( $order_id,ORDER_REJECT);
                if($GLOBALS['_DB']->DBlink->HasFailedTrans())
		{
			$GLOBALS['_DB']->DBlink->CompleteTrans();
			return false;
		}
		$GLOBALS['_DB']->DBlink->CompleteTrans();
                
                $this->db->DBlink->Execute("truncate table $tname");
   		$this->db->DBlink->Execute("drop table $tname");
		return true;
    }  
	
}

?>