<?php
/*-------------------------------------------------------------------------
 * /paytype
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'PayTypeController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'PayTypeController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'PayTypeController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'PayTypeController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'PayTypeController@delete'
]);