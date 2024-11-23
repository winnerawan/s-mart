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
	protected $category;
	protected $item;

	/** 
	*/
	public function category() {
		return Category::where([
			'category.id' => $this->category_id
		]);
	}

	/** 
	*/
	public function getCategory() {
		if ($this->category==null) {
			$this->category = $this->category()->first();
		}
		return $this->category;
	}

	/** 
	*/
	public function item() {
		return Item::where([
			'item.id' => $this->item_id
		]);
	}

	/** 
	*/
	public function getItem() {
		if ($this->item==null) {
			$this->item = $this->item()->first();
		}
		return $this->item;
	}
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