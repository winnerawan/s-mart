<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Import;

use App\Database\Smart;
use App\Service\Api;
use App\Support;
use Carbon\Carbon;
use Exception;

/**
 */
class Process extends Api\User\UserValidator
{
	/**
	 */
	protected $validatorRules = [
		'purchaseImport' => 'required|string',
        'supplier' => 'required|numeric'
	];

	/**
	 */
	protected $purchaseImportFile;
	protected $user;
	protected $now;
	protected $setting;
	protected $fileData;
	protected $fileJson;
	protected $decodeData;
	protected $insertData;
	protected $values;
	protected $purchaseImportRecordData;

	protected $total;
	protected $purchase;
    protected $supplier;

	/**
	 */
	public function validate()
	{
		if (!parent::validate()) {
			return false;
		}
        $this->supplier = Smart\Supplier::select()
            ->where('supplier.id', $this->validatorData->get('supplier'))
            ->first();
        if (!$this->supplier) {
            return false;
        }
		$this->purchaseImportFile = Smart\PurchaseImportFile::where('purchase_import_file.id', $this->validatorData->get('purchaseImport'))
            ->where('purchase_import_file.purchase_import_file_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
			->first();
		if (!$this->purchaseImportFile) {
			return false;
		}
		return true;
	}

	/**
	 */
	public function process()
	{
		try {
			return Smart\User::transaction(function () {
				// $this->prepareExtract();
				$this->processExtract();
				$this->insertRecord();
                $this->createPurchase();
				/* */				
				$this->updateImport();
				return true;
			});
		} catch (Exception $e) {
			$this->validator->errors()->add('report', $e->getMessage());
			return false;
		}
	}

	/**
	 */
	protected function processExtract()
	{
		$file = $this->purchaseImportFile;
		$path = Api\Config::get('document.storage');
		$path .= $file->getPurchaseImportFilePath()->name . '/';
		$path .= $file->file;
		/**/
		$text = implode(' ', [
			escapeshellcmd(realpath(__DIR__ . '/cli/import.py')),
			escapeshellcmd($path),
            escapeshellarg(Smart\PurchaseImportFileSetting::SHEET_NAME),
			escapeshellarg(Smart\PurchaseImportFileSetting::ROW_START),
			escapeshellarg(Smart\PurchaseImportFileSetting::ROW_FINISH),
			'2>&1'
		]);
		$this->fileData = trim(shell_exec($text));
		if (substr($this->fileData, 0, 1) !== '{') {
			throw new Exception($this->fileData);
		}
		$this->fileJson = json_decode($this->fileData, true);
		if (!is_array($this->fileJson)) {
			throw new Exception(json_last_error_msg());
		}
		if (empty($this->fileJson['data'])) {
			throw new Exception('The record data is empty.');
		}
		$this->decodeData = $this->fileJson['data'];
        // dd($this->decodeData);
	}

    /**
	 */
	protected function insertRecord()
	{
		foreach ($this->decodeData as $item) {
			$data = $this->readItemValues($item);
			if ($data != null) {
				$this->insertData[] = $data;
			}
		}
	}

    /**
	 */
	protected function createPurchase()
	{
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            $this->purchase = new Smart\Purchase();
            $this->purchase->supplier_id = $this->supplier->id;
            $this->purchase->description = sprintf('IMPORT %s', \Carbon\Carbon::now());
            $this->purchase->save();

            $this->createPurchaseItems($this->purchase);
            $this->purchase->total = $this->total;
            $this->purchase->save();
            // $this->purchase->total = $this->total;
            // $this->purchase->save();
        
            $this->createOrUpdateStocks();
            $this->updateLastPurchasePriceItems();
            // $this->insertPurchaseImportRecord();
		});
    }

    /**
	 */
	protected function updateImport()
	{
		$this->purchaseImportFile->purchase_import_file_status_id = Smart\PurchaseImportFileStatus::STATUS_IMPORTED_ID;
		$this->purchaseImportFile->save();

        $purchaseImport = new Smart\PurchaseImport();
        $purchaseImport->purchase_import_file_id = $this->purchaseImportFile->id;
        $purchaseImport->save();
	}
    /**
     */
    protected function createPurchaseItems($purchase) {
        foreach($this->insertData as $item) {
            $purchaseItem = new Smart\PurchaseItem();
            $purchaseItem->purchase_id = $purchase->id;
            $purchaseItem->category_id = $item['category_id'];
            $purchaseItem->item_id = $item['id'];
            $purchaseItem->sku = $item['sku'];
            // dd($item);
            
            $purchaseItem->price = $item['purchase_price'];
            $purchaseItem->qty = $item['quantity'];
            $purchaseItem->subtotal = ( $item['purchase_price'] * $item['quantity'] );

            $this->total += $purchaseItem->subtotal;

            $purchaseItem->save();

        }
    }

    /**
     */
    protected function getItemStock($category, $sku, $item) {
        return Smart\ItemStock::where([
            'item_stock.category_id' => $category,
            'item_stock.sku' => $sku, 
            'item_stock.item_id' => $item
        ])->first();
    }

    
    /**
     */
    protected function updateLastPurchasePriceItems() {
        foreach($this->insertData as $item) {
            $mItem = $this->getItem($item['sku']);
           
            if ($mItem->last_purchase_price==null) {
                $mItem->last_purchase_price = $item['purchase_price'];
                $mItem->save();
            } 
        }
    }
    
    /**
     */
    protected function createOrUpdateStocks() {
        foreach($this->insertData as $item) {
            $itemStock = $this->getItemStock($item['category_id'], $item['sku'], $item['id']);
            if ($itemStock==null) {
                $this->createStock($item);
            } else {
                $this->updateStock($item, $itemStock);
            }
        }
    }

    /**
     */
    protected function createStock($item) {
        $itemStock = new Smart\ItemStock();
        $itemStock->item_id = $item['id'];
        $itemStock->sku = $item['sku'];
        $itemStock->category_id = $item['category_id'];
        $itemStock->stock = $item['quantity'];
        $itemStock->purchase_price = $item['purchase_price'];
        $itemStock->selling_price = $item['selling_price'];
        $itemStock->save();
    }

    /**
     */
    protected function updateStock($item, $itemStock) {
        $itemStock->item_id = $item['id'];
        $itemStock->sku = $item['sku'];
        $itemStock->category_id = $item['category_id'];
        $itemStock->stock = $itemStock->stock + $item['quantity'];
        $itemStock->purchase_price = $item['purchase_price'];
        $itemStock->selling_price = $item['selling_price'];
        $itemStock->updated_at = \Carbon\Carbon::now();
        $itemStock->save();
    }
	

	/**
	 */
	protected function insertPurchaseImportRecord()
	{
		foreach ($this->values as $item) {
			$data = $this->readItemValues($item);
			$this->purchaseImportRecordData[] = $data;
		}
		foreach (array_chunk($this->purchaseImportRecordData, 1000) as $importRecords) {
			Smart\PurchaseImportRecord::insert($importRecords);
    	}
	}

	/**
	 */
	protected function readItemValues(array $item)
	{
        $category = $this->getCategory((string)strtolower(trim($item[1])));
        $it = $this->getItem((string)trim($item[2]));       
		if ($it!=null && $item['quantity']!=0) {
            $data['category'] = (string) strtoupper(trim($item[1]));
            $data['sku'] = (string) strtoupper(trim($item[2]));
            $data['name'] = strtoupper(trim($item[3]));
            $data['quantity'] = strtoupper(trim($item[4]));
            $data['purchase_price'] = strtoupper(trim($item[5]));
            $data['selling_price'] = strtoupper(trim($item[6]));
            $data['category_id'] = $category->id;
            $data['id'] = $it->id;
        }
        // dd($data);
		return $data;
	}

    /**
     */
    protected function getCategory($category) {
        $term = strtolower($category);
        return Smart\Category::whereRaw('lower(name) = (?)',["{$term}"])->first();
    }


    /**
     */
    protected function getItem($sku) {
        return Smart\Item::where([
            'item.sku' => $sku
        ])->first();  
    }

	// /**
	//  */
	// protected function removeDuplicates($current, $existing)
	// {
	// 	$tmpArray = array();
	// 	foreach ($current as $data1) {
	// 		$duplicate = false;
	// 		foreach ($existing as $data2) {
	// 			if ($data1['nik'] === $data2['nik'] && $data1['address'] === $data2['address']) $duplicate = true;
	// 		}
	// 		if ($duplicate === false) $tmpArray[] = $data1;
	// 	}
	// 	return $tmpArray;
	// }

}