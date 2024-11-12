<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Stock;

use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Data extends Api\User\UserValidator
{
	/**
	 */
	protected $data;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->data = Smart\ItemStock::select('item_stock.*', 'item_media.media_uid')
			->join('item', function($join) {
				$join->on('item.category_id', 'item_stock.category_id');
				$join->on('item.sku', 'item_stock.sku');
				$join->on('item.id', 'item_stock.item_id');
			})
			->leftJoin('item_media', function($join) {
				$join->on('item_media.category_id', 'item.category_id');
				$join->on('item_media.sku', 'item.sku');
				$join->on('item_media.item_id', 'item.id');
			})
			// ->orderBy('name', 'ASC')
			// ->orderBy('created_datetime', 'DESC')
			->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($item) {
		$response = $item;
		if ($item->media_uid!=null) {
			$response['image'] = Support\Media::buildFileUrlFromItemMedia($item->getItemMedia());
		} else {
			$response['image'] = null;
		}
		return $response;
	}
	/**
	 */
	protected function getData() {
		$response = [];
		foreach($this->data as $item) {
			$response[] = $this->wrapResponse($item);
		}
		return $response;
	}

	/**
	 */
	public function response(){
        $response = $this->getData();
		return $response;
	}
}