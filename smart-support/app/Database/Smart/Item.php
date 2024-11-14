<?php
/**
 */
namespace App\Database\Smart;
use Rorecek\Ulid\HasUlid;

/**
 */
class Item extends AbstractModel
{
	
	use HasUlid;
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
		'id' => 'string',
		'sku' => 'string'
	];

	/**
	 */
	protected $itemMedia;
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
	
	/**
	 */
	public function itemMedia() {
		return ItemMedia::where([
			'item_media.category_id' => $this->category_id,
			'item_media.sku' => $this->sku,
			'item_media.item_id' => $this->id,
		]);
	}

	/**
	 */
	public function getItemMedia() {
		if ($this->itemMedia==null) {
			$this->itemMedia = $this->itemMedia();
		}
		return $this->itemMedia->first();
	}
}