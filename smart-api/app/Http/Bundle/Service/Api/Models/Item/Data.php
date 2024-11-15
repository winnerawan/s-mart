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
	protected $validatorRules = [
        'query' => 'nullable|string',
        'category' => 'nullable|numeric',
    ];
	/**
	 */
	protected $data;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->data = Smart\Item::select('item.*');
			// ->leftJoin('item_media', function($join) {
			// 	$join->on('item_media.category_id', 'item.category_id');
			// 	$join->on('item_media.sku', 'item.sku');
			// 	$join->on('item_media.item_id', 'item.id');
			// });
			// ->orderBy('created_datetime', 'DESC')
		if ($this->validatorData->get('category')!=null) {
			$this->data = $this->data->where('item.category_id', $this->validatorData->get('category'));
		}
		if ($this->validatorData->get('query')!=null) {
			$this->data = $this->data->where('item.name', 'like', '%'.$this->validatorData->get('query').'%')
				->orWhere('item.sku', 'like', '%'.$this->validatorData->get('query').'%')
				->orWhere('item.description', 'like', '%'.$this->validatorData->get('query').'%');
			// $this->data = $this->data->whereLike(['item.name', 'item.sku', 'item.description'], $this->validatorData->get('query'));
		}
		$this->data = $this->data->orderBy('name', 'ASC')->get();
		return true;
	}

	/**
	 */
	protected function wrapResponse($item) {
		$response = $item;
		if ($item->media_id!=null) {
			$response['image'] = sprintf('https:%s%s', Api\Config::get('media.file'), $item->getItemMedia()->getMedia()->file);//Support\Media::buildFileUrlFromItemMedia($item->getItemMedia());
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