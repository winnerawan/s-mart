<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Import;

use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 * TODO
 */
class Delete extends Api\User\UserValidator
{
	/**
	 */
	protected $validatorRules = [
		'id' => 'required|string'
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
		$this->file = Smart\PurchaseImportFile::select()
			->where('purchase_import_file.id', $this->validatorData->get('id'))
			// ->where('purchase_import_file.purchase_import_file_status_id', Smart\MediaStatus::STATUS_TEMPORARY_ID)
			->orderByDesc('purchase_import_file.created_at')
			->first();
		if (!$this->file) {
			$this->validator->errors()->add('id', 'The file not found.');
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