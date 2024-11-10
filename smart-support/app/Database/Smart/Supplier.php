<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Supplier extends AbstractModel
{
	/**
	 */
	protected $table = 'supplier';

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
	protected $hasPurchases;

	/** 
	*/
	public function hasPurchases() {
		$hasPurchases = Purchase::where([
			'purchase.supplier_id' => $this->id
		])->count();
		if ($hasPurchases>0) {
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