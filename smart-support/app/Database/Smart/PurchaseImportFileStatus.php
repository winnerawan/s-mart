<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class PurchaseImportFileStatus extends AbstractModel
{
    /**
	 */
	const STATUS_IMPORTED_ID = 1;
    const STATUS_REMOVED_ID = 2;
    const STATUS_TEMPORARY_ID = 3;

    /**
     */
    protected $table = 'purchase_import_file_status';

    /**
     */
    protected $primaryKeys = [
        'id' => 'id'
    ];
}