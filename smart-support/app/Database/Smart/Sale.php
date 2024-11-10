<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Sale extends AbstractModel
{
	/**
	 */
	protected $table = 'sale';

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
	protected $items;
	protected $hasItems;

	/** 
	*/
	public function hasItems() {
		$hasItems = Item::where([
			'item.category_id' => $this->id
		])->count();
		if ($hasItems>0) {
			return true;
		}
		return false;
	}

	/** 
	*/
	public function items() {
		return Item::where([
			'item.category_id' => $this->id
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