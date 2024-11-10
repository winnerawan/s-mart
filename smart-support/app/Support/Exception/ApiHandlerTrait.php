<?php
/**
 */
namespace App\Support\Exception;

use Exception;
use Illuminate\Support\Facades\Request;
use Symfony\Component\Debug\Exception\FlattenException;
use Symfony\Component\HttpKernel\Exception\HttpException;
use App\Support\Response\JsonResponse;
use App\Support\Config;
use Symfony\Component\HttpKernel\Exception\HttpExceptionInterface;
use Throwable;

/**
 */
trait ApiHandlerTrait
{
	/**
	 */
	protected $response;

	/**
	 */
	protected function renderHttpException(HttpExceptionInterface $e){
		return $this->viewError($e->getStatusCode(), $e, $e->getHeaders());
	}

	/**
	 */
	protected function convertExceptionToResponse(Throwable $e){
		if (Config::isDebug()) {
			return parent::convertExceptionToResponse($e);
		} elseif ($this->response !== null) {
			return response('');
		}
		$wrap = FlattenException::create($e);
		$this->response = $this->viewError($wrap->getStatusCode(), $e, $wrap->getHeaders());
		return $this->response;
	}
	
	/**
	 */
	protected function isViewErrorExists($statusCode){
		return view()->exists("errors.{$statusCode}");
	}
	
	/**
	 */
	protected function viewError($statusCode, Exception $e, $headers){
		if (Request::expectsJson()) {
			return JsonResponse::error($statusCode)->build();
		} elseif ($this->isViewErrorExists($statusCode)) {
			return response()->view("errors.{$statusCode}", ['exception' => $e], $statusCode, $headers);
		}
		return response('', $statusCode, $headers);
	}
}