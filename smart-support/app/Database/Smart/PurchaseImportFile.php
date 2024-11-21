<?php
/**
 */
namespace App\Database\Smart;
use Rorecek\Ulid\HasUlid;
/**
 */
class PurchaseImportFile extends AbstractModel
{
    use HasUlid;
    /**
     */
    protected $table = 'purchase_import_file';

    /**
     */
    protected $primaryKeys = [
        'id' => 'id'
    ];

    /**
	 */
	protected $purchaseImportFilePath;
	protected $purchaseImportFileStatus;
    protected $purchaseImportFileMimetype;
	protected $purchaseImportRecords;
	
    /**
	 */
	public function purchaseImportFilePath(){
		return PurchaseImportFilePath::where([
			'purchase_import_file_path.id' => $this->purchase_import_file_path_id
		]);
	}

	/**
	 */
	public function getPurchaseImportFilePath(){
		if ($this->purchaseImportFilePath == null) {
			$this->purchaseImportFilePath = $this->purchaseImportFilePath()->first();
		}
		return $this->purchaseImportFilePath;
	}

	/**
	 */
	public function purchaseImportFileStatus(){
		return PurchaseImportFileStatus::where([
			'purchase_import_file_status.id' => $this->purchase_import_file_status_id
		]);
	}

	/**
	 */
	public function getPurchaseImportFileStatus(){
		if ($this->purchaseImportFileStatus == null) {
			$this->purchaseImportFileStatus = $this->purchaseImportFileStatus()->first();
		}
		return $this->purchaseImportFileStatus;
	}

	/**
	 */
	public function purchaseImportFileMimetype(){
		return PurchaseImportFileMimetype::where([
			'purchase_import_file_mimetype.id' => $this->purchase_import_file_mimetype_id
		]);
	}
	
	/**
	 */
	public function getPurchaseImportFileMimetype(){
		if ($this->purchaseImportFileMimetype == null) {
			$this->purchaseImportFileMimetype = $this->purchaseImportFileMimetype()->first();
		}
		return $this->purchaseImportFileMimetype;
	}

	/**
	 */
	public function purchaseImportRecords(){
		return PurchaseImportRecord::where([
			'purchase_import_record.purchase_import_file_id' => $this->id
		]);
	}

	/**
	 */
	public function getPurchaseImportRecords(){
		if ($this->purchaseImportRecords == null) {
			$this->purchaseImportRecords = $this->purchaseImportRecords()->get();
		}
		return $this->purchaseImportRecords;
	}
}