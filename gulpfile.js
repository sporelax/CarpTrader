const gulp        = require('gulp');
const postcss     = require('gulp-postcss');
const sourcemaps  = require('gulp-sourcemaps');
const autoprefixer= require('autoprefixer');
const uglify      = require('gulp-uglify');
const babel       = require('gulp-babel');
const del         = require('del');

gulp.task('clean', function() {
  return del(['build']);
});

gulp.task('scripts', function () {
      gulp.src('src/*.js')
      .pipe(babel( {
            presets: ['es2015','react']
      }))
      //.pipe(uglify())
      .pipe(gulp.dest('build'))
});

gulp.task('htmls', function() {
      gulp.src('src/*.html')
      .pipe(gulp.dest('build'))
})

gulp.task('autoprefixer', function () {
    return gulp.src('./src/*.css')
        .pipe(postcss([ autoprefixer() ]))
        .pipe(gulp.dest('./build'));
});

gulp.task('watch', function() {
      gulp.watch('src/*.js',['scripts']);
      gulp.watch('src/*.css',['autoprefixer']);
      gulp.watch('src/*.html',['htmls']);
})

gulp.task('default', ['watch', 'clean', 'htmls', 'scripts', 'autoprefixer']);