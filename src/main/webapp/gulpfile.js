var gulp = require('gulp');
var sass = require('gulp-sass');
var sourcemaps = require('gulp-sourcemaps');
var cleanCSS = require('gulp-clean-css');
var postcss = require('gulp-postcss');
var autoprefixer = require('autoprefixer');
var browserSync = require('browser-sync');
var reload = browserSync.reload;

gulp.task('styles', function() {
	gulp.src('src/scss/**/*.scss')
		.pipe(sourcemaps.init())
		.pipe(sass({
			includePaths: [
				'bower_components/support-for/sass',
				'bower_components/normalize-scss',
				'bower_components/bootstrap-sass/assets/stylesheets'
			],
		}).on('error', sass.logError))
		.pipe(postcss([autoprefixer]))
		.pipe(sourcemaps.write())
		.pipe(gulp.dest('dist/css/'))
		.pipe(reload({stream:true}));
});

gulp.task('default', function() {
	browserSync({
		proxy: 'localhost:8000'
	});

	gulp.watch('src/scss/**/*.scss', ['styles']);
	gulp.watch('src/**/*.html').on('change', browserSync.reload);
});
