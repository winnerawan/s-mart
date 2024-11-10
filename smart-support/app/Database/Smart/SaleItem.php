<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class SaleItem extends AbstractModel
{
	/**
	 */
	protected $table = 'sale_item';

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
	protected $casts = [
		'sale_id' => 'string',
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