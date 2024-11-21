<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class ImportController extends AbstractController
{
    /**
	 */
	public function index(){
		$response['version'] = 1;
		$response['message'] = 'Congratulations! You have found an empty page! Go celebrate this great discovery by sending the admin a coffee...';// url('');

		return $this->jsonData($response)
			->build();
    }

	/**
	 */
	public function importPurchase(Request $request)
	{
		$upload = new Models\Import\Upload($request);
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
	public function data(Request $request){
		$data = new Models\Import\Data($request);
		if (!$data->validate()) {
			return $this->jsonError()
				->params($data->getValidator()->errors())
				->build();
		}
		return $this->jsonData($data->response())
			->build();
	}

}