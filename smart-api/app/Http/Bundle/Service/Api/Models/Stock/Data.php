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
		$this->data = Smart\ItemStock::select('item_stock.*', 'item.name', 'item_media.media_id')
			->join('item', function($join) {
				$join->on('item.category_id', 'item_stock.category_id');
				$join->on('item.sku', 'item_stock.sku');
				$join->on('item.id', 'item_stock.item_id');
			})
			->leftJoin('item_media', function($join) {
				$join->on('item_media.category_id', 'item.category_id');
				$join->on('item_media.sku', 'item.sku');
				$join->on('item_media.item_id', 'item.id');
			});
		if ($this->validatorData->get('category')!=null) {
			$this->data = $this->data->where('item.category_id', $this->validatorData->get('category'));
		}
		if ($this->validatorData->get('query')!=null) {
			$this->data = $this->data->where('item.name', 'like', '%'.$this->validatorData->get('query').'%')
				->orWhere('item.sku', 'like', '%'.$this->validatorData->get('query').'%')
				->orWhere('item.description', 'like', '%'.$this->validatorData->get('query').'%');
			// $this->data = $this->data->whereLike(['item.name', 'item.sku', 'item.description'], $this->validatorData->get('query'));
		}
			// ->orderBy('name', 'ASC')
			// ->orderBy('created_datetime', 'DESC')
		$this->data = $this->data->orderBy('name', 'ASC')->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($item) {
		$response = $item;
		$response['category_name'] = $item->getCategory()->name;
		if ($item->media_id!=null) {
			$response['image'] = sprintf('https:%s%s', Api\Config::get('media.file'), $item->getItem()->getItemMedia()->getMedia()->file);//Support\Media::buildFileUrlFromItemMedia($item->getItemMedia());
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