<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Customer extends AbstractModel
{
	/**
	 */
	protected $table = 'customer';

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
	protected $hasSales;

	/** 
	*/
	public function hasSales() {
		$hasSales = Sale::where([
			'sale.customer_id' => $this->id
		])->count();
		if ($hasSales>0) {
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