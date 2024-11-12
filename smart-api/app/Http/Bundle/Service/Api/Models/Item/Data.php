<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Item;

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
		$this->data = Smart\Item::select('item.*', 'item_media.media_uid')
			->leftJoin('item_media', function($join) {
				$join->on('item_media.category_id', 'item.category_id');
				$join->on('item_media.sku', 'item.sku');
				$join->on('item_media.item_id', 'item.id');
			})
			->orderBy('name', 'ASC')
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

	/**`
	 */
	public function response(){
        $response = $this->getData();
		return $response;
	}
}