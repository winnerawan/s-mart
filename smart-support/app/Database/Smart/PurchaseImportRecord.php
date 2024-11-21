<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class PurchaseImportRecord extends AbstractModel
{
    /**
	 */
	protected $table = 'purchase_import_record';
	
	/**
     */
    protected $primaryKeys = [
        'purchase_import_file_uid' => 'purchase_import_file_uid',
        'purchase_uid' => 'purchase_uid'
    ];

    /**
     */
    protected $purchaseItems;

    /**
     */
    public function purchaseItems(){
        return Item::where([
            'item.id' => $this->purchase_id
        ]);
    }

    /**
     */
    public function getPurchaseItems(){
        if ($this->purchaseItems==null) {
            $this->purchaseItems = $this->purchaseItems()->first();
        }
        return $this->purchaseItems;
    }
}