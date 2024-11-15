<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Media;

use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Remove extends Api\User\UserValidator
{
	/**
	 */
	protected $validatorRules = [
		'id' => 'required|numeric'
	];

	/**
	 */
	protected $file;

	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->file = $this->getUser()->medias()
			->where('media.id', $this->validatorData->get('id'))
			->where('media.media_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
			->first();
		if (!$this->file) {
			$this->validator->errors()->add('uid', 'The file not found.');
			return false;
		}
		return true;
	}

	/**
	 */
	public function remove(){
		Smart\User::transaction(function(){
			$this->file->media_status_id = Smart\MediaStatus::STATUS_REMOVED_ID;
			$this->file->updated_user_id = $this->getUser()->id;
			$this->file->is_updated = 1;
			$this->file->save();
		});
	}
}