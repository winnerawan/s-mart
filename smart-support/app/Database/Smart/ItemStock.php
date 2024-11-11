<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class ItemStock extends AbstractModel
{
	/**
	 */
	protected $table = 'item_stock';

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
		'item_id' => 'item_id',
		'sku' => 'sku'
	];

	/**
	 */
	protected $casts = [
		'item_id' => 'string',
		'sku' => 'string'
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