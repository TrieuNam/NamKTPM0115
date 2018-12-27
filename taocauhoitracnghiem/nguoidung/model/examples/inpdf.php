<?php
//============================================================+
// File name   : example_039.php
// Begin       : 2008-10-16
// Last Update : 2014-01-13
//
// Description : Example 039 for TCPDF class
//               HTML justification
//
// Author: Nicola Asuni
//
// (c) Copyright:
//               Nicola Asuni
//               Tecnick.com LTD
//               www.tecnick.com
//               info@tecnick.com
//============================================================+

/**
 * Creates an example PDF TEST document using TCPDF
 * @package com.tecnick.tcpdf
 * @abstract TCPDF - Example: HTML justification
 * @author Nicola Asuni
 * @since 2008-10-18
 */


// Include the main TCPDF library (search for installation path).
require_once('tcpdf_include.php');

// create new PDF document
$pdf = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);

// set document information
$pdf->SetCreator(PDF_CREATOR);
$pdf->SetAuthor('Tên tác giả');
$pdf->SetTitle('Tiêu đề ');
$pdf->SetSubject('Chủ đề ');


// set default header data
$pdf->SetHeaderData(PDF_HEADER_LOGO, PDF_HEADER_LOGO_WIDTH, PDF_HEADER_TITLE.'', PDF_HEADER_STRING);

// set header and footer fonts
$pdf->setHeaderFont(Array(PDF_FONT_NAME_MAIN, '', PDF_FONT_SIZE_MAIN));
$pdf->setFooterFont(Array(PDF_FONT_NAME_DATA, '', PDF_FONT_SIZE_DATA));

// set default monospaced font
$pdf->SetDefaultMonospacedFont(PDF_FONT_MONOSPACED);

// set margins
$pdf->SetMargins(PDF_MARGIN_LEFT, PDF_MARGIN_TOP, PDF_MARGIN_RIGHT);
$pdf->SetHeaderMargin(PDF_MARGIN_HEADER);
$pdf->SetFooterMargin(PDF_MARGIN_FOOTER);

// set auto page breaks
$pdf->SetAutoPageBreak(TRUE, PDF_MARGIN_BOTTOM);

// set image scale factor
$pdf->setImageScale(PDF_IMAGE_SCALE_RATIO);

// set some language-dependent strings (optional)
if (@file_exists(dirname(__FILE__).'/lang/eng.php')) {
	require_once(dirname(__FILE__).'/lang/eng.php');
	$pdf->setLanguageArray($l);
}
include "../../../ketnoicsdl/ketnoiCSDL.php";
// ---------------------------------------------------------
	if(isset($_POST["create"])){	
		$da=$_POST["SoCauDeDaNhap"];
		
		$kho=$_POST["SoCauKhoDaNhap"];		
			
		$d=$_POST["idmon"];
	
		
		
// add a page
$pdf->AddPage();

// set font
// set kiểu font cho file
$pdf->SetFont('dejavuserif', '', 12);
// ghi nội dung vao file PDF
	
		$html='<p>Họ Tên...................................</p>';
		$html.='<p>Lớp...................................</p>';
$pdf->Write(0, 'Đề:'.rand(), '', 0, 'L', true, 0, false, false, 0);

// create some HTML content

$html.='<table>';
	$sql="select  id_mon from thongtin_monhoc where ten_MonHoc='$d'";
	$thucthi0=$con->query($sql);
		while($row3=mysqli_fetch_array($thucthi0)){
				$id_mon=$row3['id_mon'];
			$j=1;
				$thucthi=$con->query("select  * from dap_an where DoKho='Dễ' and idmon='$id_mon' ORDER BY RAND() LIMIT $da");
				$thucthi2=$con->query("select  * from dap_an where DoKho='Khó' and idmon='$id_mon' ORDER BY RAND() LIMIT $kho");
				while($row=mysqli_fetch_array($thucthi)){
					
					$html.=	"<tr><td> Câu hỏi " .$j." ". $row[1]."</td></tr>";
					$html.='<tr><td><ol type="A" >';
					$html.='<li style="float:left; width:40%;">'  .$row[2].'</li>';	
					$html.='<li style="float:left; width:40%;">'  .$row[3].'</li>';	
					$html.='<li style="float:left; width:40%;">' . $row[4].'</li>'	;
					$html.='<li style="float:left; width:40%;">'. $row[5].'</li>'	;
					
					$html.='</ol></td></tr>';
									
					
					++$j;
}
		while($row2=mysqli_fetch_array($thucthi2)){ 
				
					$html.=	'<tr><td> Câu hỏi ' .$j.' '. $row2[1].'</td></tr>';
					$html.='<tr><td><ol type="A" >';
					$html.='<li style="float:left; width:40%;">'  .$row2[2].'</li>';	
					$html.='<li style="float:left; width:40%;">'  .$row2[3].'</li>';	
					$html.='<li style="float:left; width:40%;">' . $row2[4].'</li>'	;
					$html.='<li style="float:left; width:40%;">'. $row2[5].'</li>'	;
					
					$html.='</ol></td></tr>';
				
				
					
						++$j;
		}	
	
	}
		$html.='</table>';
$pdf->Ln();

// set UTF-8 Unicode font
$pdf->SetFont('dejavusans', '', 10);

// output the HTML content
$pdf->writeHTML($html, true, 0, true, true);

// reset pointer to the last page
$pdf->lastPage();
}
// ---------------------------------------------------------

//Close and output PDF document
$pdf->Output('file.pdf', 'I');

//============================================================+
// END OF FILE
//============================================================+
?>