<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Import;

use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Upload extends Api\User\UserValidator
{
	/**
	 */
	protected $validatorRules = [
		'file' => 'required|file|max:8192' /* 8MB */
	];

	/**
	 */
	protected $file;
	protected $path;
	protected $mimetype;

	/**
	 */
	public function getFileSize()
	{
		return $this->file->getSize();
	}

	/**
	 */
	public function getFileName()
	{
		return $this->file->getClientOriginalName();
	}

	/**
	 */
	public function validate()
	{
		if (!parent::validate()) {
			return false;
		}
		$this->file = $this->validatorData->get('file');
		$this->mimetype = Smart\PurchaseImportFileMimetype::where('mimetype', $this->file->getClientMimeType())->first();
		if (!$this->mimetype) {
			$this->validator->errors()->add('file', sprintf('The file type not supported(%s)', $this->file->getClientMimeType()));
			return false;
		}
		$this->path = Smart\PurchaseImportFilePath::select()
			->first();
		return true;
	}

	/**
	 */
	public function upload()
	{
		Smart\User::transaction(function () {
			$purchaseImportFile = new Smart\PurchaseImportFile();
			$purchaseImportFile->purchase_import_file_path_id = $this->path->id;
			$purchaseImportFile->purchase_import_file_status_id = Smart\PurchaseImportFileStatus::STATUS_TEMPORARY_ID;
			$purchaseImportFile->purchase_import_file_mimetype_id = $this->mimetype->id;
			$purchaseImportFile->name = $this->getFileName();
			$purchaseImportFile->size = $this->getFileSize();
			$purchaseImportFile->save();
			/**/
			$purchaseImportFile->file = vsprintf('%s.%s', [static::encodeName($purchaseImportFile), $this->file->guessClientExtension()]);
			$purchaseImportFile->save();
			$directory = Api\Config::get('document.storage') . $this->path->name;
			$this->file->move($directory, $purchaseImportFile->file);
		});
	}
	
	/**
	 */
	public static function encodeName($media){
		$value = str_replace(['+','/','='], ['-','_','$'], base64_encode(json_encode([
			(string)$media->user_id,
			(string)$media->id
		])));
		return $value;
	}
	
	/**
	 */
	public static function decodeName($value){
		$value = json_decode(base64_decode(str_replace(['-','_','$'], ['+','/','='], $value), true), true);
		if (is_array($value) && count($value) == 2) {
			return Smart\Media::where(['user_id' => $value[0], 'id' => $value[1]])->first();
		}
	}
}