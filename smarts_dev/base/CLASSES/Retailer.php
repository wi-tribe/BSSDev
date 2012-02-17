<?php

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/BRMIntegration.php");
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/POS.php");
include_once(CLASSDIR."/Order.php");
include_once(CLASSDIR."/Commission.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Logging.php");
include_once(LIB."/adodb5/adodb.inc.php");

Class Retailer
{
    /**
 * Store local class messages and errors
 *
 * @var string
 */
   public $LastMsg;
/**
 * Store database connection pointer
 *
 * @var object
 */
   private $db;
   private $conf;
   private $mlog;

/**
 * Contructor
 *
 */
   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->conf=new config();
        $this->mlog=new Logging();
   }

   public function prePorcessOrder($values)
   {
       $noOrder=0;
       for($loop=1; $loop<=$values['total_rows']; $loop++)
       {
            if($values['denomination_'.$loop]==null)
           {
                $noOrder++;
                if($noOrder==$values['total_rows'])
               {
                   $this->LastMsg.="Order Is Empty <br>";
               }
           } //if ends
           else
           {
           if($values['denomination_'.$loop]!=null)
           {
              if($values['quantity_'.$loop]==null)
              {
                  $this->LastMsg.="Please Enter Quantity for Face Value ".$values['denomination_'.$loop]." <br>";
              }
           
              if($values['Manual_Select_'.$loop]!=null)
              {
                  if($values['voucher_serial_'.$loop]==null)
                  {
                      $this->LastMsg.="No Voucher Selected of Face Value ".$values['denomination_'.$loop]." <br>";
                  }

                  if(count($values['voucher_serial_'.$loop])!=$values['quantity_'.$loop])
                  {
                      $this->LastMsg.=" Selection ".$loop." => Quantity = ".$values['quantity_'.$loop]." not Matched with Seleted Vouchers = ".count($values['voucher_serial_'.$loop])." of Face Value ".$values['denomination_'.$loop]."  <br>";
                  }
              }
           
              else
              {
                      if($values['start_serial_'.$loop]==null)
                          {
                              $this->LastMsg.="Please Enter Start Serial of Face Value ".$values['denomination_'.$loop]." <br>";
                          }
                      if($values['end_serial_'.$loop]==null)
                      {
                          $this->LastMsg.="Please Enter End Serial of Face Value ".$values['denomination_'.$loop]." <br>";
                      }
//                      if($values['start_serial_'.$loop]!=null && $values['end_serial_'.$loop]!=null)
//                      {
//                          if(count($this->generateVoucherArray($values['start_serial_'.$loop], $values['end_serial_'.$loop]))!=$values['quantity_'.$loop])
//                          {
//                               $this->LastMsg.=" Selection ".$loop." => Quantity = ".$values['quantity_'.$loop]." not Matched with Seleted Vouchers = ".count($this->generateVoucherArray($values['start_serial_'.$loop], $values['end_serial_'.$loop]))." of Face Value ".$values['denomination_'.$loop]."  <br>";
//                          }
//                      }
                  } //inner else ends
               } //outer else if ends
           } //outer else ends
       } //forloop end
         if($this->LastMsg!=null)
		return false;
         
	return true;   
   }

   public function processRequestedOrder($values,$se_channel,$retailer_id,$retailer_channel)
   {
     $logging = new Logging();
     $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Processing Retailer Order, RetailerID:$retailer_id, Retailer ShopID: $retailer_channel Order Processed by SE(ID) :".$values['SE']);
     $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Pre Processing Posted Values for retailer owner(ID): $retailer_id processed by SE(ID): ".$values['SE']."");
     if(!$this->prePorcessOrder($values))
     {
        return false;
     }

     $voucher = new Voucher();
     $order = new Order();

     $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Array Generation of Voucher Ranges for retailer shop(ID): $retailer_channel processed by SE: ".$values['SE']);
     //  Array Creation for voucher ranges
      for($loop=1; $loop<=$values['total_rows']; $loop++)
       {
         if($values['denomination_'.$loop]!=null)
         {
                // Generating Arrays of Denominations
                 $voucher_range_serial = $values['voucher_serial_'.$loop];
                 if(!$values['Manual_Select_'.$loop])
                 {
                     $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Generating Voucher Range: ".$values['start_serial_'.$loop]." to ".$values['end_serial_'.$loop]." of face value: ".$values['denomination_'.$loop]);
                     $voucher_range_serial = $this->generateVoucherArray($values['start_serial_'.$loop], $values['end_serial_'.$loop]);
                 }
                 else
                 {
                     $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Manual Selection: ".$voucher_range_serial[0]." to ".$voucher_range_serial[count($voucher_range_serial)-1]." of face value: ".$values['denomination_'.$loop]);
                 }
                 for($i=0;$i<count($voucher_range_serial);$i++)
                 {
                     $VoucherSerials[$values['denomination_'.$loop]][] = $voucher_range_serial[$i];
                 }
                 
                 $totalQuantity[$values['denomination_'.$loop]] += $values['quantity_'.$loop];
         }

       }// for loop endsy

        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Checking for Duplicate Vouchers for retailer shop(ID): $retailer_channel processed by SE(ID): ".$values['SE']);
       // Checking  Denomination Vouchers for Duplication n Validation
       foreach($VoucherSerials as $key => $vouchers)
        {
            if(!$this->checkOrderDuplicateVouchers($vouchers,$key))
           {
               $this->LastMsg.="<br> Order Contains Duplicate Vouchers ";
               $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Duplicate Vouchers found in Selection Vouchers:$vouchers[0] to ".$vouchers[count($vouchers)-1]);
               return false;
           }
        }

        foreach($VoucherSerials as $key => $voucherz)
        {
             $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Validating Serial of face value: $key ,Owner :SE(ID)".$values['SE']." ,Shop: $se_channel");
             $vouchIDReturned = $voucher->validateSerialOwner($values['SE'],$se_channel,$voucherz);

               if(!$vouchIDReturned)
               {
                   $this->LastMsg.= "<BR> ".$voucher_obj->LastMsg;
                   $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Vouchers not with user: SE(ID)= ".$values['SE']." at Shop(ID)= $se_channel, Vouchers:$voucherz[0]  to ".$vouchers[count($vouchers)-1]);
                    return false;
               }

               if($totalQuantity[$key]!= count($vouchIDReturned))
		{
			$this->LastMsg.= " <br> Please assign ".$totalQuantity[$key]." total vouchers instead of ".count($vouchIDReturned)." to process this order of Denomination ".$key;
                         $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Please assign ".$totalQuantity[$key]." total vouchers instead of ".count($vouchIDReturned)." to process this order of Denomination ".$key." Vouchers:$voucherz[0]  to ".$vouchers[count($vouchers)-1]);
			return false;
		}

              $voucherIDs[$key] = $vouchIDReturned;
        }

        $Discount=0;
        $total_amnt=0;

        //checking whether this is retailer first order
        $retailer_order = $this->getRetailerOrders($retailer_id);
        $retailer_desc = "Retailer Order";
        if(!$retailer_order && $values['discount_applied']==true) //its retailer first order & discount applied
        {
            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Retailer ID: $retailer_id first Order and Discount Applied");
            $Discount = 5000;
            $retailer_desc = "Retailer 1st Order";
        }
        
            foreach($voucherIDs as $key => $vouchers)
            {
                $total_amnt += $key * count($vouchers);
                $total_count += count($vouchers);
                $perdenomAmnt[$key] = count($vouchers);
                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Order Details: Denomination = $key , Total Voucher = ".count($vouchers));
            }
        
            if($Discount>0)
            {
                if($total_amnt < 25000)
                {
                    $this->LastMsg.= " <br> First Order Must be of 25000 Amount, You Placed Order of ".$total_amnt;
                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." First Order Must be of 25000 Amount, You Placed Order of ".$total_amnt." , Retailer ID: $retailer_id first Order and Discount Applied");
                    return false;
                }
            }

            // check all given vouchers are in Inactive state
            $GLOBALS['_DB']->DBlink->StartTrans();

            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Generating Order Request for retailerOwnerID:$retailer_id , retailer(ShopID): $retailer_channel");
            //generate order
            foreach($voucherIDs as $key => $voucherz)
            {
                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Pre Processing Order Request of face value:$key [total vouchers:".count($voucherz)." for retailer: $retailer_channel , retailerID: ".$values['Retailer']."]");
                    if(!$order->preProcessRequest($values['Retailer'], $key, count($voucherz), $retailer_desc))
                     {
                        $this->LastMsg.= $order->error;
                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Order generation preprocessor failed");
                        return false;
                     }

                     $user_info = $GLOBALS['_USER']->getUserInfo($values['Retailer']);
                     $order_from_channel = $user_info[0]['user_channel_id'];

                     $total_price = $key * count($voucherz);
                     $total_items = count($voucherz);
                     $insert = "order_type_id, order_date, order_by, order_from_channel, total_price, total_items,order_desc,order_denomination";
                     $vals = "1, CURRENT_DATE, ".$values['Retailer'].", $order_from_channel, $total_price, $total_items, '".$retailer_desc."', $key";

                      $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Generating Order: insert into Order_T ($insert) values($vals)");
                      
                        if(($order_id=$this->db->InsertRecord(ORDER_T, $insert, $vals))==null)
                        {
                                $this->LastMsg.= "Order Generation Failed <br>";
                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Order Generation Failed ($insert) values($vals)");
                                return false;
                        }
                     
                        $insert = "order_id, order_status_id,action_by, action_date";
                        $vals = "$order_id,".ORDER_REQUEST.",".$values['Retailer'].",CURRENT_DATE";
                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." insert into Order_State_T ($insert) values($vals)");
                        if(($result=$this->db->InsertRecord(ORDER_STATE_T, $insert, $vals))==null)
                        {
                                $this->LastMsg.= "Order Status ID Insertion Failed <br> Operation Rollback Please try again";
                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Insertion Operation Failed Table: vims_order_state ($insert) values($vals)");
                                return false;
                        }

                    $OrderIDs[$key][] = $order_id;
            }
            
           // process order
           $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Processing Order (RetailerID :$retailer_id processed by SE(ID): ".$values['SE'].")");
            foreach($voucherIDs as $key => $voucherz)
            {
                foreach($OrderIDs as $denom => $order_id)
                {
                   if($key==$denom)
                   {
                       //=================== new technique ====================//
                        $tracker_obj = new Tracker();

                        $denom_start = $voucher->getVoucherDenomination($voucherz[0]);
                        $denom_end=$voucher->getVoucherDenomination($voucherz[count($voucherz)-1]);

                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Currently Processing Order: Order(ID) = $order_id[0] Order By= $retailer_id Order From Channel=$retailer_channel");

                        $order_details =  $order->getOrder($order_id[0]);
                        $order_details = $order_details[0];

                                if($denom_start != $order_details['order_denomination']|| $denom_end != $order_details['order_denomination'])
                                {
                                        $this->LastMsg.= "Please assign vouchers of price ".$order_details['order_denomination']." PKR";
                                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." OrderID($order_id[0]) Face Value Mismatched with Form Provided FaceValue [start denom:$denom_start, end_denom:$denom_end]");
                                        return false;
                                }

                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Assigning VouchersIDs [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] to Order: $order_id[0]");
                                // Assign Items to Order
                                $insert = "order_id, voucher_id";

                                foreach($voucherz as $voucher_id)
                                {
                                        $vals = " ".$order_details['order_id'].",$voucher_id";
                                        if(($result=$this->db->InsertRecord(ORDER_ITEM_T, $insert, $vals))==null)
                                        {
                                                $this->LastMsg.="Failed to assign vouchers to order <br> Operation Rollback Please try again";
                                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed to Assign vouchers to order: $order_id[0]");
                                                return false;
                                        }
                                }

                                //========================== move inventory code ==============================//
                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." preProcessing Inventory before inventory movement [SE(SHOP):$se_channel,retailer(SHOP):$retailer_channel,retailerOwner:$retailer_id,vouchersids:[ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                                if(!$tracker_obj->preProcessInventory($se_channel,$retailer_channel,$retailer_id,$voucherz))
                                     {
                                            $this->LastMsg.="Failed to process inventory <br> Operation Rollback Please try again";
                                            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed to preProcess inventory movement [SE(SHOP):$se_channel,retailer(SHOP):$retailer_channel,retailerOwner:$retailer_id,vouchersids:[ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                                            return false;
                                     }

                                     // getting movement type
                                    
                                    $se_channel=empty($se_channel)?0:$se_channel;
                                    $retailer_channel=$retailer_channel=='-1'?0:$retailer_channel;
                                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Getting Movement Type for fromChannel:$se_channel toChannel:$retailer_channel");
                                    $query="
                                    select * from ".$this->db->db_prefix."transfer_type
                                            where
                                            transfer_type_name=
                                            (
                                                    select concat(concat(coalesce(fromc.channel_type_name,'Null'),'_'),coalesce(toc.channel_type_name,'Sold'))
                                                    from


                                                    (select channel_type_name, 1 as j from
                                                    ".$this->db->db_prefix."channel c inner join ".$this->db->db_prefix."channel_type ct
                                                    on c.channel_type_id=ct.channel_type_id
                                                    where channel_id=coalesce(nullif(0,0),$retailer_channel)
                                                    ) toc

                                                    full outer join


                                                    (select channel_type_name, 1 as j from
                                                    ".$this->db->db_prefix."channel c inner join ".$this->db->db_prefix."channel_type ct
                                                    on c.channel_type_id=ct.channel_type_id
                                                    where channel_id=coalesce(nullif(0,0),$se_channel)
                                                    ) fromc

                                                    on fromc.j=toc.j
                                            )
                                    ";

                                    $data=$this->db->CustomQuery($query);
                                    
                                    if($data==null)
                                    {
                                        $this->LastMsg.="Movement not authorized. Please contact administrator <br> Operation Rollback Please try again";
                                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Movement not authorized for fromChannel:$se_channel toChannel:$retailer_channel");
                                        return false;
                                    }
                                    $movement_type = $data[0]['transfer_type_id'];

                                        $action_by = $values['SE'];
                                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Clossing SE =".$values['SE']." asigned Invenory ");
                                        foreach($voucherz as $voucher_id)
                                        {
                                            $query="update vims_tracker set end_date=current_timestamp where voucher_id = $voucher_id and end_date is null";
                                            
                                            if(!$this->db->CustomModify($query))
                                            {
                                                    $this->LastMsg.="Tracker Update failed <br> Operation Rollback Please try again";
                                                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Clossing SE =".$values['SE']." asigned Invenory of OrderID=".$order_id[0]);
                                                    return false;
                                            }
                                        }
                                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Moving Inventory to RetailerChannel: $retailer_channel, actionBy:".$values['SE']." VouchersIDs:[ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] of OrderID=$order_id[0] ");
                                        foreach($voucherz as $arr) {
                                            $insert = "from_channel_id,to_channel_id,action_by,action_date,voucher_id,transfer_type_id, end_date, assigned_to,transfer_status";
                                            $vals = "'".$se_channel."','".$retailer_channel."','".$values['SE']."',current_timestamp ,'".$arr."', $movement_type, NULL,'$retailer_id','1'";

                                            if(($result=$this->db->InsertRecord($this->conf->db_prefix."tracker", $insert, $vals))==null) {
                                                $this->LastMsg.="Failed Insertion: $arr<br> Operation Rollback Please try again";
                                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Moving Inventory to RetailerChannel: $retailer_channel, actionBy:".$values['SE']." VouchersIDs:[ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ]");
                                                 return false;
                                            }
                                        }
                               //============================================================================//
                               $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Marking OrderID:$order_id[0] Status Assigned,ActionBy: ".$values['SE']);
                                $insert = "order_id, order_status_id,action_by, action_date";
                                $vals = "$order_id[0],".ORDER_ASSIGN.",".$values['SE'].",CURRENT_DATE";

                                if(($result=$this->db->InsertRecord(ORDER_STATE_T, $insert, $vals))==null)
                                {
                                        $this->LastMsg.= "Order Status ID Insertion Failed <br> Operation Rollback Please try again";
                                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Marking OrderID:$order_id[0] Status Assigned,ActionBy: ".$values['SE']);
                                        return false;
                                }
                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Receving Inventory OrderID:$order_id[0] Status,ActionBy: ".$retailer_id);
                                $insert = "order_id, order_status_id,action_by, action_date";
                                $vals = "$order_id[0],".ORDER_RECIEVE.",".$retailer_id.",CURRENT_DATE";

                                if(($result=$this->db->InsertRecord(ORDER_STATE_T, $insert, $vals))==null)
                                {
                                        $this->LastMsg.= "Order Status ID Insertion Failed <br> Operation Rollback Please try again";
                                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Receving Inventory OrderID:$order_id[0] Status,ActionBy: ".$retailer_id);
                                        return false;
                                }
                       //======================================================//
                   } //if(denom == key) ends
                } // OrderIDs for loop ends
            } // process order for loop ends

             
            //sell retailers inventory
            foreach($voucherIDs as $key => $voucherz)
            {
                $tracker = new Tracker();
                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Activating and Marking Vouchers as Sold by RetailerID:$retailer_id,retailerChannel(ID):$retailer_channel, VouchersIDs [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] of Face Value=$key");
                $statusList = array(VOUCHER_USED, VOUCHER_EXPIRED, VOUCHER_STOLEN, VOUCHER_LOST, VOUCHER_DAMAGED);
                $nonUsableVoucherCount=0;
                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Checking Vouchers Status in BRM, VouchersIDs[ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] of Face Value=$key");
                foreach($voucherz as $voucher_id)
                {
                    $query = "	SELECT * from device_t,DEVICE_VOUCHER_T 
                                WHERE poid_id0 = obj_id0 and poid_id0 in ($voucher_id)
                                 and ( state_id in (".VOUCHER_USED.",".VOUCHER_EXPIRED.",".VOUCHER_STOLEN.",".VOUCHER_LOST.",".VOUCHER_DAMAGED.") 
                                       or 
                                       sysdate > u2s(DEVICE_VOUCHER_T.EXPIRATION+18000)
                                     )";
                    
                    $rs = $this->db->CustomQuery($query);
                    if($rs!=null)
                    {
                        $nonUsableVoucherCount++;
                    }                    
                }

                if($nonUsableVoucherCount>0)
                {
                    $this->LastMsg.= 'This operation is not allowed for some the vouchers. <br> Operation Rollback Please try again <br> Check Voucher Status/Expiration';
                    
                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: VouchersIds [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] State is not Inactive, This operation is not allowed for some the vouchers");
                    return false;
                }
               
                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Activating Cards in BRM, VoucherIds[ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                foreach($voucherz as $poid_id0)
                {
                        $query = "update device_t set state_id = ".VOUCHER_ACTIVE." where poid_id0 ='$poid_id0'";
                        if(!$this->db->CustomModify($query))
                        {
                            $this->LastMsg.= 'Unable to Activate Vouchers <br> Operation Rollback Please try again';
                            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Unable to activate Cards in BRM Vouchers [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                            return false;
                        }
                }

                //=========================== sell retailer assigned Inventory ==================//
                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Marking Cards Sold in Smarts Vouchers [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                   if(!$tracker_obj->preProcessInventory($se_channel,$retailer_channel,$retailer_id,$voucherz))
                     {
                           $this->LastMsg.="Failed to process inventory <br> Operation Rollback Please try again";
                           $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Unable to Preprocess Vouchers [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] before marking sold");
                           return false;
                     }
               
                      // getting movement type
                        $from_channel_id=empty($retailer_channel)?0:$retailer_channel;
                        $to_channel_id='-1'=='-1'?0:$to_channel_id;
                        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Getting Movement Type for fromChannel:$from_channel_id , ToChannel:$to_channel_id");
                        $query="
                        select * from ".$this->db->db_prefix."transfer_type
                                where
                                transfer_type_name=
                                (
                                        select concat(concat(coalesce(fromc.channel_type_name,'Null'),'_'),coalesce(toc.channel_type_name,'Sold'))
                                        from


                                        (select channel_type_name, 1 as j from
                                        ".$this->db->db_prefix."channel c inner join ".$this->db->db_prefix."channel_type ct
                                        on c.channel_type_id=ct.channel_type_id
                                        where channel_id=coalesce(nullif(0,0),$to_channel_id)
                                        ) toc

                                        full outer join


                                        (select channel_type_name, 1 as j from
                                        ".$this->db->db_prefix."channel c inner join ".$this->db->db_prefix."channel_type ct
                                        on c.channel_type_id=ct.channel_type_id
                                        where channel_id=coalesce(nullif(0,0),$from_channel_id)
                                        ) fromc

                                        on fromc.j=toc.j
                                )
                        ";
                        
                        $data=$this->db->CustomQuery($query);

                        if($data==null)
                        {
                            $this->LastMsg.="Movement not authorized. Please contact administrator <br> Operation Rollback Please try again";
                            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Getting Movement Type for fromChannel:$from_channel_id , ToChannel:$to_channel_id");
                            return false;
                        }
                        $movement_type = $data[0]['transfer_type_id'];
                        
            //        TODO: assuming sessions for user_id and channel_id
            //        starting the update insert loop
                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Clossing Retailer Assigned Vouchers [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                    foreach($voucherz as $voucher_id)
                    {
                        $query="update vims_tracker set end_date=current_timestamp where voucher_id = $voucher_id and end_date is null and assigned_to = $retailer_id";

                        if(!$this->db->CustomModify($query))
                        {
                                $this->LastMsg.="Tracker Update failed <br> Operation Rollback Please try again";
                                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Clossing Retailer Assigned Vouchers [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]."]");
                                return false;
                        }
                    }
                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Marking Vouchers [ ".$voucherz[0]." : ".$voucherz[count($voucherz)-1]." ] sold ");
                    foreach($voucherz as $arr) {
                        $insert = "from_channel_id,to_channel_id,action_by,action_date,voucher_id,transfer_type_id, end_date, assigned_to,transfer_status";
                        $vals = "'".$retailer_channel."','-1','".$retailer_id."',current_timestamp ,'".$arr."', $movement_type, NULL,'0','1'";

                        if(($result=$this->db->InsertRecord($this->conf->db_prefix."tracker", $insert, $vals))==null) {
                            $this->LastMsg.="Failed Insertion: $arr<br> Operation Rollback Please try again";
                            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Insertion Failed");
                             return false;
                        }
                    }
            }
            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Generating Invoice for RetailerOrder: RetailerOwnerID:$retailer_id,RetailerChannel:$retailer_channel Processed by SE(ID):".$values['SE']);
           // generate Invoice n apply commission rule n populate bridge table
            $commission_obj = new Commission();
            $channel_obj = new Channel();
            $channel_detail = $channel_obj->getChannelDetails($retailer_channel);
            $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Getting Commission Rule for RetailerChannelID: $retailer_channel");
            $commission_rule = $commission_obj->getcommissionValue($retailer_channel, $channel_detail[0]['channel_type_id'], $applied_to);
              if(!$commission_rule)
              {
                  $this->LastMsg =  $commission_obj->LastMsg;
                  $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Getting Commission Rule for RetailerChannelID: $retailer_channel");
                  return false;
              }
              print("Commission Value :".$commission_rule[0]['value']."%<br>");
              foreach($perdenomAmnt as $key => $vouchers)
              {
                print("$key Vouchers :".$perdenomAmnt[$key]." of Total Amount :".$key*$perdenomAmnt[$key]."<br>");
              }
              print("Total Items :".$total_count."<br>");
              print("Total Amount".$total_amnt."<br>");
              
              $commission_amnt = ($commission_rule[0]['value']/100)*$total_amnt;
              print("Total Commissioned Amount: ".$commission_amnt."<br>");
			  
             // print("WHT : 10% <br>");
             // $WHT_RATIO = 10/100;
             // $WHT_ON_COMMISSION = $commission_amnt*$WHT_RATIO;
             // print("WHT on Commission Amnt :".$WHT_ON_COMMISSION."<br>");

              
              print("Discount Amnt :".$Discount."<br>");
              //$WHT_ON_DISCOUNT = $WHT_RATIO * $Discount;
             // $WHT_ON_DISCOUNT = 0;
             // print("WHT on Discount Amnt :".$WHT_ON_DISCOUNT."<br>");
              $Net_payable = $total_amnt - $commission_amnt -$Discount;// + $WHT_ON_COMMISSION +$WHT_ON_DISCOUNT;
              print("Net Payables Are :".$Net_payable);

             $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Generating Invoice: RetailID: $retailer_id,Channel Name:".$channel_detail[0]['channel_name']."
                               ,total amount:$total_amnt,total count:$total_count,commission rule id: ".$commission_rule[0]['commission_id']."
                               ,commission value:".$commission_rule[0]['value'].",commission amount: $commission_amnt,net payables: $Net_payable");
             
              $invoice_id = $this->generateInvoice($values['SE'], $retailer_id, $channel_detail[0]['channel_name'],
                                     $total_amnt, $total_count, $commission_rule[0]['commission_id'],
                                     $commission_rule[0]['value'], $commission_amnt,$Discount, $Net_payable, 'Cash');
              if(!$invoice_id)
              {
                  $this->LastMsg.="Invoice Generation Failed <br> Operation Rollback Please try again";
                  $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Generating Invoice: RetailID: $retailer_id,Channel Name:".$channel_detail[0]['channel_name']."
                               ,total amount:$total_amnt,total count:$total_count,commission rule id: ".$commission_rule[0]['commission_id']."
                               ,commission value:".$commission_rule[0]['value'].",commission amount: $commission_amnt,net payables: $Net_payable");
                  return false;
              }
              $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Inserting Invoice ID ($invoice_id) in Bridge Table ".INVOICE_ORDER_T."");
               foreach($OrderIDs as $denom => $order_id)
                {
                    $insert = "INVOICE_ID,ORDER_ID";
                    $vals="".$invoice_id.",".$order_id[0]."";
                    $query = "insert into ".INVOICE_ORDER_T."($insert) values($vals)";
                    $result = $this->db->CustomQuery($query);
                    $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Inserting Invoice ID ($invoice_id) Order ID($order_id[0])");
                }
         //if any transaction fails rollback
                
        if($GLOBALS['_DB']->DBlink->HasFailedTrans())
        {
                $GLOBALS['_DB']->DBlink->CompleteTrans();
                $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Failed: Rolling Back Complete Transaction for Retailer: RetailerID: $retailer_id, RetailChannel:$retailer_channel, Processed by:".$values['SE']);
                return false;
        }

        //commit
        $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Committing Transaction for RetailerID: $retailer_id, RetailChannel:$retailer_channel, Processed by:".$values['SE']." InvoiceID:$invoice_id");
        $GLOBALS['_DB']->DBlink->CompleteTrans();
        return true;
}

   public function checkOrderDuplicateVouchers($array,$denomination)
   {
       $found=false;
       for($outerindex=0;$outerindex<count($array);$outerindex++)
       {
           for($innerindex=$outerindex+1;$innerindex<count($array);$innerindex++)
           {
               if($array[$outerindex]==$array[$innerindex])
               {
                   print("Voucher ".$array[$innerindex]." Duplicated in Selection of Denomination ".$denomination."<br>");
                   $found=true;
               }
           }
       }
       if($found)
           return false;

       return true;

        
   } // CheckDuplicate Function Ends
   
   public function generateVoucherArray($voucher_serial_start,$voucher_serial_end)
   {
        $start=substr($voucher_serial_start,getLastChar($voucher_serial_start)+1);
    	$end=substr($voucher_serial_end,getLastChar($voucher_serial_end)+1);
    	$prefix=strtoupper(substr($voucher_serial_start,0,getLastChar($voucher_serial_start)+1));

        //iterative insert
        $StserialLength = strlen($start);
        $EnSerialLength = strlen($end);

        //checking whether the starting and ending serial starts with zero
        if($start[0]=="0" && $end[0]=="0")
        {
            $startWithZero=true;
        }

        if($StserialLength!=$EnSerialLength)
        {
            echo "Starting and Ending Serial Length Do not match";
            return false;
        }
        $initial=$start;
    	for($start; $start<=$end; $start++)
    	{
            if($initial!=$start)
             {
                if($startWithZero)
                {
                    if(strlen("0".$start)!=$StserialLength || strlen("0".$start)!=$EnSerialLength)
                    {
                        echo "Generated Serial do match with Start and End Serial Provided";
                        return false;
                    }
                    $vouchers[]=$prefix."0".$start;
                }
             else
                 {
                    if(strlen($start)!=$StserialLength || strlen($start)!=$EnSerialLength)
                    {
                        echo "Generated Serial do match with Start and End Serial Provided";
                        return false;
                    }
                     $vouchers[]=$prefix.$start;
                 }
             }
             else
             {
                 $vouchers[]=$prefix.$start;
             }
    	}
//        for($start; $start<=$end; $start++)
//    	{
//    		$vouchers[]=$prefix.$start;
//    	}
        return $vouchers;
   }
   
   public function getRetailerOrders($retailer_id)
   {
       $query="select * from ".ORDER_T." where order_by = $retailer_id";

       $data = $this->db->CustomQuery($query);

       if($data!=null)
       {
           return $data;
       }
       else
           return false;
   }

    public function updateRetailer()
        {
             $status="";
             if($_POST['status']!=null)
             {
                $status = " status = ".$_POST['status'].", ";
             }
             $query = "update ".USER_T." set
                                         first_name = '".$_POST['first_name']."',
                                         last_name = '".$_POST['last_name']."',
                                         username =  '".$_POST['user_name']."',
                                         email = '".$_POST['email']."',
                                         user_team_id = ".$_POST['team_id'].",
                                         user_role_id = ".$_POST['user_role_id'].",
                                         parent_user_id = '".$_POST['parent_user_id']."',
                                         user_channel_id = ".$_POST['user_channel_id'].",".$status."
                                         modified_date = CURRENT_DATE
                                         where user_id = '".$_POST['user_id']."' ";
                                      //  print($query);
             $update = $GLOBALS['_DB']->CustomModify($query);
             if(!$update)
                 {
                    $this->LastMsg.="Operation Failed";
                    return false;
                 }
              return true;
        }

        private function generateInvoice($action_by,$order_by,$order_channel_name,$total_amnt,$total_items,$commission_rule_id,$comm_value,$commission_amnt,$discount_amnt,$net_amnt,$mode_of_payment)
        {
         $insert = "ACTION_BY,INVOICE_DATE,ORDER_TYPE,ORDER_DATE,CHANNEL_NAME,ORDER_BY,TOTAL_ITEMS,TOTAL_PRICE
                    ,COMMISSION_ID,COMMISSION_VALUE,COMMISSION_AMOUNT
                    ,DISCOUNT_ID,DISCOUNT_VALUE,DISCOUNT_AMOUNT
                    ,NET_AMOUNT,PAYMENT_MODE";

         $vals="".$action_by.",CURRENT_DATE,'Inventory Request',CURRENT_DATE,'".$order_channel_name."','".$order_by."'
                ,".$total_items.",".$total_amnt.",'".$commission_rule_id."','".$comm_value."'
                ,'".$commission_amnt."','','".$discount_amnt."','".$discount_amnt."','".$net_amnt."'
                ,'".$mode_of_payment."'";
         
         //print("insert into ".INVOICE_T."($insert) values($vals)");
         $result = $this->db->InsertRecord(INVOICE_T,$insert,$vals);
        if($result!=null)
        {
            return $result;
        }
       $this->LastMsg.="Insertion failed";
       return false;
        }
}

?>
