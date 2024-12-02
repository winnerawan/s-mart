<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class PurchaseItem extends AbstractModel
{
	/**
	 */
	protected $table = 'purchase_item';

	// /**
	//  */
	// protected $visible = [
	// 	'id',
	// 	'name',
	// 	'icon',
	// 	'position' 
	// ];
	/**
	 */
	protected $primaryKeys = [
		'purchase_id' => 'purchase_id',
		'item_id' => 'item_id',
	];
	/**
	 */
	protected $casts = [
		'purchase_id' => 'string',
		'item_id' => 'string',
		'sku' => 'string'
	];

	/**
	 */
	protected $items;

	/** 
	*/
	public function items() {
		return Item::where([
			'item.id' => $this->item_id,
			'item.sku' => $this->sku
		]);
	}

	/** 
	*/
	public function getItems() {
		if ($this->items==null) {
			$this->items = $this->items()->get();
		}
		return $this->items;
	}

}