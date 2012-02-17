<?php

include_once('../../vims_config.php');
include_once(LIB."/phpexcel/PHPExcel/Worksheet.php");

class Export
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
 * Contructor
 *
 */
   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->mlog=new Logging();
        $this->conf=new config();
   }

   public function CSVExport($dataHeading,$data,$docInfo,$fname)
   {
        $fp = fopen('/tmp/abc.csv',"w+"); // $fp is now the file pointer to file $filename
        $data_count = count($data);

	if($fp){
            for($info=0;$info<sizeof($docInfo);$info++)
            {
                fputcsv($fp, $docInfo[$info],",");
            }
   
            fputcsv($fp,$dataHeading,",");
            for($i = 0 ; $i < $data_count; $i ++)
            {
                fputcsv($fp, $data[$i], ",");  //  Write information to the file
            }
	    fclose($fp);  
	} else {
	    echo "Error saving file!";
	}
        header("Content-type: application/octet-stream");
	header("Content-Disposition: attachment; filename=$fname");
	header("Pragma: no-cache");
	header("Expires: 0");
        readfile("/tmp/abc.csv");

   }
   
   public function export($headers,$valuesArray,$docInfo,$Searchcriteria,$headers2,$valuesArray2)
   {
	$objPHPExcel = new PHPExcel();
        $creater = $valuesArray[0]['username'];
	$objPHPExcel->getProperties()->setCreator($creater)
                                     ->setLastModifiedBy("Maarten Balliauw")
                                     ->setTitle("Office 2007 XLSX Test Document")
                                     ->setSubject("Office 2007 XLSX Test Document")
                                     ->setDescription("Test document for Office 2007 XLSX, generated using PHP classes.")
                                     ->setKeywords("office 2007 openxml php")
                                     ->setCategory("Test result file");
     $ActiveSheet=0;
     if($valuesArray!=null)
     {
        $index=1;
        $objPHPExcel->setActiveSheetIndex($ActiveSheet);
        $ActiveSheet++;
        $objDrawing = new PHPExcel_Worksheet_Drawing();
        $objDrawing->setPath('/var/www/html/dev/smarts_dev/vims/images/logo.jpg');
        $objDrawing->setCoordinates('A'."$index");
        $objDrawing->getShadow()->setVisible(true);
        $objDrawing->setWorksheet($objPHPExcel->getActiveSheet());

        $objPHPExcel->getActiveSheet()->mergeCells('D'."$index".':F'."$index");
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getFont()->setBold(true);
        $objPHPExcel->getActiveSheet()->getCell('D'."$index")->setValue($docInfo['Heading']);
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getAlignment()->setWrapText(true);
         $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getAlignment()->applyFromArray(
                 array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        
         
        $index++;
        $geneDate=" Generation Date: ";
        $date = date("m/d/Y - H:i:s", time());
        $date = split('-',$date);

        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue($geneDate);
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($date[0]);
         $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

          $objPHPExcel->getActiveSheet()->mergeCells('E'."$index".':F'."$index");
          $search_criteria=" Search Criteria: ";
          $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue($search_criteria);
          $objPHPExcel->getActiveSheet()->getStyle('E'."$index".':F'."$index")->getAlignment()->setWrapText(true);
           $objPHPExcel->getActiveSheet()->getStyle('E'."$index".':F'."$index")->getAlignment()->applyFromArray(
                 array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

         $index++;
         $geneTime = " Generation Time:";
         $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue($geneTime);
         $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($date[1]);
         $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Region:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Region']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );


        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('Generated By:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($Searchcriteria['Created By']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Channel:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Channel']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );
 
        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('Start Date:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($docInfo['Start Date']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('SE/CCE:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Searched User']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );
        
        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('End Date:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($docInfo['End Date']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );
        
        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Channel Type');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Channel Type']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );
        $index = $index+2;
        
        foreach($headers as $col)
        {
            $objPHPExcel->getActiveSheet()->setCellValue( $col['index']."$index", $col['label']);
            $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index")->getFont()->setBold(true);
            $objPHPExcel->getActiveSheet()->getColumnDimension($col['index'])->setWidth(18);

        }

        $styleThinBlackBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THIN,
			'color' => array('argb' => 'FF000000'),
		),
	),
);
 foreach($headers as $col)
     {
        $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index".':'.$col['index']."$index")->applyFromArray($styleThinBlackBorderOutline);
     }

$styleThickBrownBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THICK,
                        
			'color' => array('argb' => 'FF993300'),
		),
	),
);

        $index++;
        foreach($valuesArray as $row)
        {
            foreach($headers as $col)
            {
               $objPHPExcel->getActiveSheet()->setCellValue($col['index']."$index", $row[$col['name']]);
            }            
           $index++;
        }
        
        $objPHPExcel->getActiveSheet()->setTitle($docInfo['CCTitle']);
   }

   if($valuesArray2!=null)
     {
       if($ActiveSheet>0)
       {
        $newSheet = $objPHPExcel->createSheet($ActiveSheet);
       }
        $objPHPExcel->setActiveSheetIndex($ActiveSheet);
        
        $index=1;
        $objDrawing = new PHPExcel_Worksheet_Drawing();
        $objDrawing->setPath('/var/www/html/dev/smarts_dev/vims/images/logo.jpg');
        $objDrawing->setCoordinates('A'."$index");
        $objDrawing->getShadow()->setVisible(true);
        $objDrawing->setWorksheet($objPHPExcel->getActiveSheet());

        $objPHPExcel->getActiveSheet()->mergeCells('D'."$index".':F'."$index");
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getFont()->setBold(true);
        $objPHPExcel->getActiveSheet()->getCell('D'."$index")->setValue($docInfo['Heading']);
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getAlignment()->setWrapText(true);
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getAlignment()->applyFromArray(
                 array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index++;
        $geneDate=" Generation Date: ";
        $date = date("m/d/Y - H:i:s", time());
        $date = split('-',$date);

        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue($geneDate);
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($date[0]);
         $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

          $objPHPExcel->getActiveSheet()->mergeCells('E'."$index".':F'."$index");
          $search_criteria=" Search Criteria: ";
          $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue($search_criteria);
          $objPHPExcel->getActiveSheet()->getStyle('E'."$index".':F'."$index")->getAlignment()->setWrapText(true);
          $objPHPExcel->getActiveSheet()->getStyle('E'."$index".':F'."$index")->getAlignment()->applyFromArray(
                 array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

         $index++;
         $geneTime = " Generation Time:";
         $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue($geneTime);
         $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($date[1]);
         $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Region:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Region']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('Generated By:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($Searchcriteria['Created By']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Channel:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Channel']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('Start Date:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($docInfo['Start Date']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('SE/CCE:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Searched User']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('End Date:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($docInfo['End Date']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index = $index+2;

        foreach($headers2 as $col)
        {
            $objPHPExcel->getActiveSheet()->setCellValue( $col['index']."$index", $col['label']);
            $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index")->getFont()->setBold(true);
            $objPHPExcel->getActiveSheet()->getColumnDimension($col['index'])->setWidth(18);

        }

        $styleThinBlackBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THIN,
			'color' => array('argb' => 'FF000000'),
		),
	),
);
 foreach($headers2 as $col)
     {
        $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index".':'.$col['index']."$index")->applyFromArray($styleThinBlackBorderOutline);
     }

$styleThickBrownBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THICK,

			'color' => array('argb' => 'FF993300'),
		),
	),
);

        $index++;
        foreach($valuesArray2 as $row)
        {
            foreach($headers2 as $col)
            {
               $objPHPExcel->getActiveSheet()->setCellValue($col['index']."$index", $row[$col['name']]);
            }
           $index++;
        }
        $objPHPExcel->getActiveSheet()->setTitle($docInfo['ReTitle']);
   }

        header('Content-Type: application/vnd.ms-excel');
        $fname = $docInfo['Filename'];
	header('Content-Disposition: attachment;filename='."$fname");
	header('Cache-Control: max-age=0');


	$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel5');
	ob_clean();
	$objWriter->save('php://output');
        $objPHPExcel->disconnectWorksheets();
        exit();

   }
   
    public function RetentionSummaryExport($headers,$documentInfo,$valuesArray,$Searchcriteria,$grand_count)
    {
        $objPHPExcel = new PHPExcel();
        $creater = $Searchcriteria['Created By'];
	$objPHPExcel->getProperties()->setCreator($creater)
                                     ->setLastModifiedBy("Maarten Balliauw")
                                     ->setTitle("Office 2007 XLSX Test Document")
                                     ->setSubject("Office 2007 XLSX Test Document")
                                     ->setDescription("Test document for Office 2007 XLSX, generated using PHP classes.")
                                     ->setKeywords("office 2007 openxml php")
                                     ->setCategory("Test result file");

         $index=1;
        $objDrawing = new PHPExcel_Worksheet_Drawing();
        $objDrawing->setPath('/var/www/html/dev/smarts_dev/vims/images/logo.jpg');
        $objDrawing->setCoordinates('A'."$index");
        $objDrawing->getShadow()->setVisible(true);
        $objDrawing->setWorksheet($objPHPExcel->getActiveSheet());

        $objPHPExcel->getActiveSheet()->mergeCells('D'."$index".':F'."$index");
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getFont()->setBold(true);
        $objPHPExcel->getActiveSheet()->getCell('D'."$index")->setValue($documentInfo['Title']);
        $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getAlignment()->setWrapText(true);
         $objPHPExcel->getActiveSheet()->getStyle('D'."$index".':K'."$index")->getAlignment()->applyFromArray(
                 array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index++;
        $geneDate=" Generation Date: ";
        $date = date("m/d/Y - H:i:s", time());
        $date = split('-',$date);

        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue($geneDate);
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($date[0]);
         $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

          $objPHPExcel->getActiveSheet()->mergeCells('E'."$index".':F'."$index");
          $search_criteria=" Search Criteria: ";
          $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue($search_criteria);
          $objPHPExcel->getActiveSheet()->getStyle('E'."$index".':F'."$index")->getAlignment()->setWrapText(true);
           $objPHPExcel->getActiveSheet()->getStyle('E'."$index".':F'."$index")->getAlignment()->applyFromArray(
                 array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

            $index++;
         $geneTime = " Generation Time:";
         $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue($geneTime);
         $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($date[1]);
         $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Region:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Region']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );


        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('Generated By:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($Searchcriteria['Created By']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

         $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('Channel:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['Channel']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('Start Date:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($documentInfo['Start_date']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $objPHPExcel->getActiveSheet()->getCell('E'."$index")->setValue('CCE:');
        $objPHPExcel->getActiveSheet()->getCell('F'."$index")->setValue($Searchcriteria['CCE']);
        $objPHPExcel->getActiveSheet()->getStyle('F'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

         $index++;
        $objPHPExcel->getActiveSheet()->getCell('B'."$index")->setValue('End Date:');
        $objPHPExcel->getActiveSheet()->getCell('C'."$index")->setValue($documentInfo['End_date']);
        $objPHPExcel->getActiveSheet()->getStyle('C'."$index")->getAlignment()->applyFromArray(
      		array(
      			'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
      			'rotation'   => 0,
      			'wrap'       => true
                    )
          );

        $index = $index+3;

        foreach($headers as $col)
        {
            $objPHPExcel->setActiveSheetIndex(0)->setCellValue( $col['index']."$index", $col['label']);
            $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index")->getFont()->setBold(true);
            $objPHPExcel->getActiveSheet()->getColumnDimension($col['index'])->setWidth(18);
        }
         $styleThinBlackBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THIN,
			'color' => array('argb' => 'FF000000'),
		),
	),
);

 foreach($headers as $col)
     {
        $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index".':'.$col['index']."$index")->applyFromArray($styleThinBlackBorderOutline);
     }

$styleThickBrownBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THICK,

			'color' => array('argb' => 'FF993300'),
		),
	),
);

         $index++;
         $valueStartIndex = $index;
         $total_records = count($valuesArray['ATTEMPTED']);
         for($i=0;$i<$total_records;$i++)
         {
          foreach($headers as $col)
            {
               $objPHPExcel->setActiveSheetIndex(0)->setCellValue($col['index']."$index", $valuesArray[$col['name']][$i]);
            }
        $index++;
         }

     $index = $index-1;
     foreach($headers as $col)
     {
        $objPHPExcel->getActiveSheet()->getStyle($col['index']."$index".':'.$col['index']."$index")->applyFromArray($styleThinBlackBorderOutline);
     }

        $styleThickBrownBorderOutline = array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
        'rotation'   => 0,
      	'wrap'       => true,
	'borders' => array(
		'outline' => array(
			'style' => PHPExcel_Style_Border::BORDER_THICK,

			'color' => array('argb' => 'FF993300'),
		),
            ),
        );

        $objPHPExcel->getActiveSheet()->setTitle($documentInfo['Title']);
	$objPHPExcel->setActiveSheetIndex(0);

        header('Content-Type: application/vnd.ms-excel');
        $fname =$documentInfo['Filename'];
	header('Content-Disposition: attachment;filename='."$fname");
	header('Cache-Control: max-age=0');

        $objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel5');
	ob_clean();
	$objWriter->save('php://output');
        $objPHPExcel->disconnectWorksheets();
        exit();
   }

}

?>
