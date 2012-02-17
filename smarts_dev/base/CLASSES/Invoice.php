<?php
/*************************************************
* @Auhor: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
**************************************************/
include_once(CLASSDIR."/Discount.php");

class Invoice
{

 /**
 * Store local class messages and errors
 *
 * @var string
 */

    public $LastMsg=null;
    private $db;
    private $mlog;
    private $conf;
/**
 * Store database connection pointer
 *
 * @var object
 */


/**
 * Contructor
 *
 */
   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->mlog=new Logging();
        $this->conf=new config();
   }

   public function generateInvoice()
   {
         $discount_amnt=0;
         $discount_value;
         
         if($_POST['discount']==null) {$net_amnt = $_POST['total_price'] - $_POST['commisssion_amount'];}
         else
             {$net_amnt = $this->calculateDiscount($_POST['discount'],&$discount_amnt,&$discount_value);}
         
         $insert = "ORDER_ID,ACTION_BY,INVOICE_DATE,ORDER_TYPE,ORDER_DATE,CHANNEL_NAME,ORDER_BY
                    ,CARD_PRICE,TOTAL_ITEMS,TOTAL_PRICE,COMMISSION_ID,COMMISSION_VALUE,COMMISSION_AMOUNT
                    ,DISCOUNT_ID,DISCOUNT_VALUE,DISCOUNT_AMOUNT,NET_AMOUNT,PAYMENT_MODE,PAYMENT_DESC";
         
         $vals="".$_POST['order_id'].",".$_SESSION['user_id'].",CURRENT_DATE,'".$_POST['order_type_name']."'
                ,'".$_POST['order_date']."','".$_POST['from_channel']."','".$_POST['order_by']."'
                ,".$_POST['order_denomination'].",".$_POST['total_items']."
                ,".$_POST['total_price'].",'".$_POST['commission_id']."','".$_POST['commisssion_value']."'
                ,'".$_POST['commisssion_amount']."','".$_POST['discount']."','".$discount_value."','".$discount_amnt."','".$net_amnt."'
                ,'".$_POST['payment_mode']."','".$_POST['payment_descrition']."'";

       //$query ="insert into vims_invoice ($insert) value($vals)";
        // print($query);
       $result = $this->db->InsertRecord(INVOICE_T,$insert,$vals);
        if($result)
        {
            return true;
        }
       $this->LastMsg.="Insertion failed";
       return false;
   }

   private function calculateDiscount($discount_id,$discount_amnt,$discount_value)
   {
         
        $disc_obj = new Discount();
        $disc_value = $disc_obj->getdiscDetails($discount_id);
        $disc_type = $disc_obj->getdisctypeName($disc_value[0]['discount_type_id']);

        if($disc_type[0]['discount_type_name']=='percentage')
        {
            $discount_value = $disc_value[0]['value'];
            $discount_amnt = $_POST['total_price']*($disc_value[0]['value']/100);
        }

        if($disc_type[0]['discount_type_name']=='denomination')
        {
            $discount_value = $disc_value[0]['value'];
            $discount_amnt = $disc_value[0]['value'];
        }

        if($disc_type[0]['discount_type_name']=='quantity')
        {
            $discount_value = $disc_value[0]['value'];
            $discount_amnt = $disc_value[0]['value'] * $_POST['order_denomination'];
        }

        $net_amount = $_POST['total_price'] - $_POST['commisssion_amount'] - $discount_amnt;

        return $net_amount;
   }

   public function getinvoiceDetails($order_id)
   {
        $query = "select * from ".INVOICE_T."
                   where order_id  = ".$order_id."";

        $data = $this->db->CustomQuery($query);
        if($data!=null)
        {
            return $data;
        }
        $this->LastMsg.="Data not found";
        return false;
       
   }

}

?>
