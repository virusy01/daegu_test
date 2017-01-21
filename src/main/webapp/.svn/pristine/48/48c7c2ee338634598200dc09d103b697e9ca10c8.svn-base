<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes"/>
		<meta name="theme-color" content="#fff"/>
		<title>광명시 사회적기업 성과관리시스템</title>

		<link rel="manifest" href="/manifest.json"/>
		<link rel="stylesheet" type="text/css" href="/styles/pikaday.css"/>
		<script>

			// setup Polymer options
			window.Polymer = {
				lazyRegister: false,
				dom: 'shadow'
			};

			// load webcomponents polyfills
			(function () {
				if ('registerElement' in document && 'import' in document.createElement('link') && 'content' in document.createElement('template')) {
					// browser has web components
				} else {
					// polyfill web components
					var e = document.createElement('script');
					e.src = '/bower_components/webcomponentsjs/webcomponents-lite.min.js';
					document.getElementsByTagName('head')[0].appendChild(e);
				}
			})();

			// load pre-caching service worker
			// if ('serviceWorker' in navigator) {
			// 	window.addEventListener('load', function () {
			// 		navigator.serviceWorker.register('/service-worker.js');
			// 	});
			// }
		</script>
		<script src="/scripts/d3.min.js"></script>
		<script src="/scripts/SimpleBarChart.js"></script>
		<script src="/scripts/RadarChart.js"></script>
		<script src="/scripts/HStackChart.js"></script>
		<script src="/scripts/c3.min.js"></script>
		<script src="/scripts/moment.min.js"></script>
		<script src="/scripts/accounting.min.js"></script>
		<script src="/scripts/pikaday.js"></script>
		<link rel="import" href="/elements/sck-app.html"/>

		<style>
			@import url( '//cdn.jsdelivr.net/font-nanum/1.0/nanumgothic/nanumgothic.css');

			body {
				font-family: 'Nanum Gothic', sans-serif;
				color: #333;
				font-size: 12px;
				margin: 0;
				background-color: #eee;
			}
			input:required:invalid {
				outline: none;
			}

			.tip {
				position: absolute;
				top: 0;
				left: 0;
				z-index: 9999;
				visibility: hidden;
				border: 1px solid rgba(0,0,0,.15);
				background-color: #fff;
				overflow: hidden;
				padding: 8px;
				text-align: left;
				font-size: 9pt;
				border-radius: 4px;
				box-shadow: 0 6px 12px rgba(0,0,0,.175);
				line-height: 150%;
			}

			.hcharttip {
				position: absolute;
				top: 0;
				left: 0;
				z-index: 9999;
				visibility: hidden;
			}

		</style>

	</head>
	<body>

		<sck-app></sck-app>

	</body>
</html>
