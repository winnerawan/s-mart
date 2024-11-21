<?php
namespace App\Http\Bundle\Service\Api\Models\Export;

use Illuminate\Http\Request;
use PhpOffice\PhpSpreadsheet\Writer\Xls;
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Style\Border;
use PhpOffice\PhpSpreadsheet\Style\Fill;
use Maatwebsite\Excel\Concerns\WithColumnFormatting;
use Barryvdh\Snappy\Facades\SnappyPdf as SnappyPdf;
use PhpOffice\PhpSpreadsheet\Style\NumberFormat;
use Maatwebsite\Excel\Events\AfterSheet;
use Maatwebsite\Excel\Concerns\ShouldAutoSize;
use Maatwebsite\Excel\Concerns\FromView;
use Illuminate\Contracts\View\View;
use App\Service\Api;
use App\Http\Bundle\Service\Api\Models\Item;

/**
 */
class ExportTemplatePurchase extends Api\User\UserValidator implements WithColumnFormatting, FromView, ShouldAutoSize
{

    /**
	 */
	protected $data;
    /**
	 */
	public function __construct(Request $request, Item\Data $data)
	{
		parent::__construct($request);
        $this->data = $data;
	}

    /**
	 */
	public function getFilenameExcel()
	{
		return sprintf('Template-Import - %s.xls', \Carbon\Carbon::now());
	}

    /**
	 */
	public function view(): View
	{
		return view('page.supervisor.master_item_export_excel', [
			'__this' => $this
		]);
	}

    /**
	 */
	public function export()
	{
		$spreadsheet = new Spreadsheet();
		$sheet = $spreadsheet->getActiveSheet();
		$sheet->setCellValue('A1', 'NO.');
		$sheet->setCellValue('B1', 'KATEGORI');
		$sheet->setCellValue('C1', 'SKU');
		$sheet->setCellValue('D1', 'ITEM');
		$sheet->setCellValue('E1', 'HARGA BELI');
		$sheet->setCellValue('F1', 'HARGA JUAL');

		$styleArray = array(
			'fill' => array(
				'type' => Fill::FILL_SOLID,
				'color' => array('rgb' => 'ffff00')
			),
			'font'  => array(
				'color' =>   array('rgb' => '000000')
			),
			'borders' => array(
				'allborders' => array(
					'style' => Border::BORDER_THICK,
					'color' => array('rgb' => '000000'),
				),
			),
		);
		 
		$spreadsheet->getActiveSheet()->getStyle('A1:N1')->getFill()->setFillType(\PhpOffice\PhpSpreadsheet\Style\Fill::FILL_SOLID)
			->getStartColor()->setARGB('E5E4E2');
		$spreadsheet->getActiveSheet()->getStyle('A1:N1')->getFont()->setBold(true);
		$spreadsheet->getActiveSheet()->getStyle('A1:N1')->applyFromArray($styleArray);
		$spreadsheet->getActiveSheet()->setShowGridLines(true);
		foreach(range('A','N') as $columnID) {
			$spreadsheet->getActiveSheet()->getColumnDimension($columnID)
				->setAutoSize(true);
		}
		$spreadsheet->getDefaultStyle()
			->getBorders()
			->getTop()
				->setBorderStyle(Border::BORDER_THIN);
		$spreadsheet->getDefaultStyle()
			->getBorders()
			->getBottom()
				->setBorderStyle(Border::BORDER_THIN);
		$spreadsheet->getDefaultStyle()
			->getBorders()
			->getLeft()
				->setBorderStyle(Border::BORDER_THIN);
		$spreadsheet->getDefaultStyle()
			->getBorders()
			->getRight()
				->setBorderStyle(Border::BORDER_THIN);
		
		$cell = 2;
		foreach($this->data->getData() as $i => $record) {
	 
			$sheet->setCellValue('A'.$cell, $i+1);
            $sheet->setCellValue('B'.$cell, $record->getCategory() ? strtoupper($record->getCategory()->name) : '-');
			$sheet->setCellValue('C'.$cell, strtoupper($record->sku));
			$sheet->setCellValue('D'.$cell, strtoupper($record->name));
			$sheet->setCellValue('E'.$cell, $record->last_purchase_price?:0);
			$sheet->setCellValue('F'.$cell, 0);
			
			
			$cell++;
		}
		$writer = new Xls($spreadsheet);
		header('Content-Type: application/vnd.ms-excel');
		header('Content-Disposition: attachment;filename='.$this->getFilenameExcel());
		header('Cache-Control: max-age=0');
		$writer->save('php://output');
		//return Excel::download($this, $this->getFilenameExcel(),  \Maatwebsite\Excel\Excel::XLS);
	}

    /**
	 */
    public function columnFormats(): array
	{
		return [
			'C' => NumberFormat::FORMAT_TEXT,
            'D' => NumberFormat::FORMAT_TEXT,
            'E' => NumberFormat::FORMAT_TEXT,
		];
	}

    /**
	 */
	public function registerEvents(): array
	{
		return [
			AfterSheet::class => function (AfterSheet $event) {
				// $event->sheet->styleCells(
				// 	'C2:C1000',
				// 	[
				// 		'alignment' => [
				// 			'horizontal' => \PhpOffice\PhpSpreadsheet\Style\Alignment::HORIZONTAL_RIGHT,
				// 		],
				// 	]
				// );
			},
		];
	}
}