<?php
/**
 */
namespace App\Database\Smart;
use Rorecek\Ulid\HasUlid;
/**
 */
class PurchaseImport extends AbstractModel
{
    use HasUlid;
  
    /**
     */
    protected $table = 'purchase_import';

    /**
     */
    protected $primaryKeys = [
        'id' => 'id'
    ];

    /**
	 */
    protected $purchaseImportFile;
    
}