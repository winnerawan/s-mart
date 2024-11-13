<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Purchase extends AbstractModel
{
	/**
	 */
	protected $table = 'purchase';

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
		'id' => 'string',
	];
	/**
	 */
	protected $items;
	protected $supplier;
	protected $pieces;
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
	public function supplier() {
		return Supplier::where([
			'supplier.id' => $this->supplier_id
		]);
	}

	/** 
	*/
	public function getSupplier() {
		if ($this->supplier==null) {
			$this->supplier = $this->supplier()->first();
		}
		return $this->supplier;
	}

	/** 
	*/
	public function items() {
		return PurchaseItem::where([
			'purchase_item.purchase_id' => $this->id
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

	/** 
	*/
	public function pieces() {
		return $this->items()->sum('qty');;
	}

}