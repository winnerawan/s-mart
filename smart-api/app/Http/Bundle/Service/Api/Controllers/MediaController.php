<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class MediaController extends AbstractController
{
    /**
	 */
	public function data(Request $request){
		$data = new Models\Media\Data($request);
		if (!$data->validate()) {
			return $this->jsonError()
				->params($data->getValidator()->errors())
				->build();
		}
		return $this->jsonData($data->response())
			->build();
	}
	
	/**
	 */
	public function upload(Request $request){
		$upload = new Models\Media\Upload($request);
		if (!$upload->validate()) {
			return $this->jsonError()
				->params($upload->getValidator()->errors())
				->build();
		}
		$upload->upload();
		return $this->jsonSuccess()
			->build();
	}

	/**
	 */
	public function editorUpload(Request $request){
		$upload = new Models\Media\EditorUpload($request);
		if (!$upload->validate()) {
			return $this->jsonError()
				->params($upload->getValidator()->errors())
				->build();
		}
		$upload->upload();
		return $this->jsonData($upload->response())
			->build();
	}
	
	/**
	 */
	public function remove(Request $request){
		$remove = new Models\Media\Remove($request);
		if (!$remove->validate()) {
			return $this->jsonError()
				->params($remove->getValidator()->errors())
				->build();
		}
		$remove->remove();
		return $this->jsonSuccess()
			->build();
	}
}