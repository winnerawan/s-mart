<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Import;

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
		$this->fit = Api\Config::get('document.fit');
		$this->file = Api\Config::get('document.file');
		return true;
	}

	/**
	 */
	public function response(){
		return $this->convertDocumentData($this->selectDocument());
	}

	/**
	 */
	protected function selectDocument(){
		return $this->getUser()->documents()
			->where('purchase_import_file.purchase_import_file_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
			->orderByDesc('purchase_import_file.created_at')
			->skip($this->getSkip())
			->take($this->getTake())
			->get();
	}

	/**
	 */
	protected function convertDocumentData($docs){
		$response = [];
		foreach ($docs as $doc) {
			$response[] = $this->convertMediaItem($doc);
		}
		return $response;
	}

	/**
	 */
	protected function convertMediaItem($doc){
		$response['id'] = $doc->id;
		$response['name'] = $doc->name;
		$response['file'] = $doc->file;
		$response['path'] = $doc->getPurchaseImportFilePath()->name;
		$response['datetime'] = Support\Format::formatDateTime($doc->created_at);
		// $response['fit_baseurl'] = $this->fit;
		$response['file_baseurl'] = $this->file;
        $response['url'] = sprintf('https:%s%s', Api\Config::get('document.file'), $doc->file);
		return $response;
	}
}