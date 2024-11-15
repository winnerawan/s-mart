<?php
/*-------------------------------------------------------------------------
 * /item
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'ItemController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'ItemController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'ItemController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'ItemController@updatePosition'
]);

$router->post('/updateTempQty', [
	'as' => '/updateTempQty',
	'uses' => 'ItemController@updateTempQty'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'ItemController@delete'
]);