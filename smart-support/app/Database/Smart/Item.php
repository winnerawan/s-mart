<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Item extends AbstractModel
{
	/**
	 */
	protected $table = 'item';

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
		// 'id' => 'string'
	];
	/**
	 */
	public function hasStocks() {
		$hasStocks = Item::where([
			'item_stock.id' => $this->item_id,
			'item_stock.category_id' => $this->category_id,
			'item_stock.sku' => $this->category_id
		])->count();
		if ($hasStocks>0) {
			return true;
		}
		return false;
	}
	

}