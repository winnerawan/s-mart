<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Media;

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
		'page' => 'nullable|numeric',
	];

	/**
	 */
	protected $fit;
	protected $file;

	/**
	 */
	public function getPage(){
		return max($this->validatorData->getInt('page'), 0);
	}

	/**
	 */
	public function getSkip(){
		return  min($this->getPage() * $this->getTake(), PHP_INT_MAX);
	}

	/**
	 */
	public function getTake(){
		return 20;
	}

	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->fit = Api\Config::get('media.fit');
		$this->file = Api\Config::get('media.file');
		return true;
	}

	/**
	 */
	public function response(){
		return $this->convertMediaData($this->selectMedias());
	}

	/**
	 */
	protected function selectMedias(){
		return $this->getUser()->medias()
			->where('media.media_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
			->orderByDesc('media.created_datetime')
			->skip($this->getSkip())
			->take($this->getTake())
			->get();
	}

	/**
	 */
	protected function convertMediaData($medias){
		$response = [];
		foreach ($medias as $media) {
			$response[] = $this->convertMediaItem($media);
		}
		return $response;
	}

	/**
	 */
	protected function convertMediaItem($media){
		$response['id'] = $media->id;
		$response['name'] = $media->name;
		$response['file'] = $media->file;
		$response['path'] = $media->getMediaPath()->name;
		$response['datetime'] = Support\Format::formatDateTime($media->created_datetime);
		// $response['fit_baseurl'] = $this->fit;
		$response['file_baseurl'] = $this->file;
        $response['url'] = sprintf('https:%s%s', Api\Config::get('media.file'), $media->file);
		return $response;
	}
}