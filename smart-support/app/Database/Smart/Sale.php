<?php
/**
 */
namespace App\Database\Smart;
use Rorecek\Ulid\HasUlid;

/**
 */
class Sale extends AbstractModel
{
	use HasUlid;
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
	protected $casts = [
		'id' => 'string',
	];

	/**
	 */
	protected $items;
	protected $customer;
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
		return SaleItem::where([
			'sale_item.sale_id' => $this->id
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
	public function customer() {
		return Customer::where([
			'customer.id' => $this->customer_id
		]);
	}

	/** 
	*/
	public function getCustomer() {
		if ($this->customer==null) {
			$this->customer = $this->customer()->first();
		}
		return $this->customer;
	}

	/** 
	*/
	public function pieces() {
		return $this->items()->sum('qty');;
	}

}