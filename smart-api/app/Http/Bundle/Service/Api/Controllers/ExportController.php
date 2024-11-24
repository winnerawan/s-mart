<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class ExportController extends AbstractController
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
	public function exportTemplatePurchase(Request $request)
	{
		$data = new Models\Item\Data($request);
		if (!$data->validate()) {
			return $this->jsonData('error')
			    ->build();
		}
		$export = new Models\Export\ExportTemplatePurchase($request, $data);
		return $export->export();
	}

}