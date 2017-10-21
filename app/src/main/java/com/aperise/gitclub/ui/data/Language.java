package com.aperise.gitclub.ui.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.aperise.gitclub.R;

/**
 * Created by le on 5/27/17.
 */

public class Language {

    public static final String JAVA = "Java";
    public static final String PYTHON = "Python";
    public static final String JAVASCRIPT = "JavaScript";
    public static final String SHELL = "Shell";
    public static final String CPP = "C++";
    public static final String GO = "Go";
    public static final String C = "C";
    public static final String HTML = "HTML";
    public static final String COFFEE_SCRIPT = "CoffeeScript";
    public static final String MAKE_FILE = "Makefile";
    public static final String APACHE_CONF = "ApacheConf";
    public static final String CSHARP = "C#";
    public static final String JUPYTER_NOTEBOOK = "JupyterNotebook";
    public static final String EMACS_LISP = "Emacs Lisp";
    public static final String TYPE_SCRIPT = "TypeScript";
    public static final String CMAKE = "CMake";
    public static final String D = "D";
    public static final String RACKET = "Racket";
    public static final String ERLANG = "Erlang";
    public static final String SWIFT = "Swift";
    public static final String EAGLE = "Eagle";
    public static final String CLOJURE = "Clojure";
    public static final String HASKELL = "Haskell";
    public static final String LUA = "Lua";
    public static final String TEX = "TeX";
    public static final String RUBY = "Ruby";
    public static final String OBJECTIVE_C = "Objective-C";
    public static final String CSS = "CSS";
    public static final String PHP = "PHP";
    public static final String PERL = "Perl";
    public static final String STANDARDML = "Standard ML";
    public static final String OCAML = "OCaml";
    public static final String GROOVY = "Groovy";
    public static final String KOTLIN = "Kotlin";
    public static final String SCALA = "Scala";


    @TargetApi(Build.VERSION_CODES.M)
    public static int getColor(Context context, String language) {
        int v = Build.VERSION.SDK_INT;
        int color = 0x000000;
        if (v >= Build.VERSION_CODES.M) {
            if (JAVA.equals(language)) {
                color = context.getColor(R.color.lang_java);
            } else if (PYTHON.equals(language)) {
                color = context.getColor(R.color.lang_python);
            } else if (JAVASCRIPT.equals(language)) {
                color = context.getColor(R.color.lang_js);
            } else if (SHELL.equals(language)) {
                color = context.getColor(R.color.lang_shell);
            } else if (CPP.equals(language)) {
                color = context.getColor(R.color.lang_cpp);
            } else if (GO.equals(language)) {
                color = context.getColor(R.color.lang_go);
            } else if (C.equals(language)) {
                color = context.getColor(R.color.lang_c);
            } else if (HTML.equals(language)) {
                color = context.getColor(R.color.lang_html);
            } else if (COFFEE_SCRIPT.equals(language)) {
                color = context.getColor(R.color.lang_coffeescript);
            } else if (MAKE_FILE.equals(language)) {
                color = context.getColor(R.color.lang_makefile);
            } else if (APACHE_CONF.equals(language)) {
                color = context.getColor(R.color.lang_apacheconf);
            } else if (CSHARP.equals(language)) {
                color = context.getColor(R.color.lang_csharp);
            } else if (JUPYTER_NOTEBOOK.equals(language)) {
                color = context.getColor(R.color.lang_jupyternotebook);
            } else if (EMACS_LISP.equals(language)) {
                color = context.getColor(R.color.lang_emacslisp);
            } else if (TYPE_SCRIPT.equals(language)) {
                color = context.getColor(R.color.lang_typescript);
            } else if (CMAKE.equals(language)) {
                color = context.getColor(R.color.lang_cmake);
            } else if (D.equals(language)) {
                color = context.getColor(R.color.lang_d);
            } else if (RACKET.equals(language)) {
                color = context.getColor(R.color.lang_racket);
            } else if (ERLANG.equals(language)) {
                color = context.getColor(R.color.lang_erlang);
            } else if (SWIFT.equals(language)) {
                color = context.getColor(R.color.lang_swift);
            } else if (EAGLE.equals(language)) {
                color = context.getColor(R.color.lang_eagle);
            } else if (CLOJURE.equals(language)) {
                color = context.getColor(R.color.lang_clojure);
            } else if (HASKELL.equals(language)) {
                color = context.getColor(R.color.lang_haskell);
            } else if (LUA.equals(language)) {
                color = context.getColor(R.color.lang_lua);
            } else if (TEX.equals(language)) {
                color = context.getColor(R.color.lang_tex);
            } else if (RUBY.equals(language)) {
                color = context.getColor(R.color.lang_ruby);
            } else if (OBJECTIVE_C.equals(language)) {
                color = context.getColor(R.color.lang_objc);
            } else if (CSS.equals(language)) {
                color = context.getColor(R.color.lang_css);
            } else if (PHP.equals(language)) {
                color = context.getColor(R.color.lang_php);
            } else if (PERL.equals(language)) {
                color = context.getColor(R.color.lang_perl);
            } else if (STANDARDML.equals(language)) {
                color = context.getColor(R.color.lang_standardml);
            } else if (OCAML.equals(language)) {
                color = context.getColor(R.color.lang_ocaml);
            } else if (GROOVY.equals(language)) {
                color = context.getColor(R.color.lang_groovy);
            } else if (KOTLIN.equals(language)) {
                color = context.getColor(R.color.lang_kotlin);
            } else if (SCALA.equals(language)) {
                color = context.getColor(R.color.lang_scala);
            }
        } else {
            if (JAVA.equals(language)) {
                color = context.getResources().getColor(R.color.lang_java);
            } else if (PYTHON.equals(language)) {
                color = context.getResources().getColor(R.color.lang_python);
            } else if (JAVASCRIPT.equals(language)) {
                color = context.getResources().getColor(R.color.lang_js);
            } else if (SHELL.equals(language)) {
                color = context.getResources().getColor(R.color.lang_shell);
            } else if (CPP.equals(language)) {
                color = context.getResources().getColor(R.color.lang_cpp);
            } else if (GO.equals(language)) {
                color = context.getResources().getColor(R.color.lang_go);
            } else if (C.equals(language)) {
                color = context.getResources().getColor(R.color.lang_c);
            } else if (HTML.equals(language)) {
                color = context.getResources().getColor(R.color.lang_html);
            } else if (COFFEE_SCRIPT.equals(language)) {
                color = context.getResources().getColor(R.color.lang_coffeescript);
            } else if (MAKE_FILE.equals(language)) {
                color = context.getResources().getColor(R.color.lang_makefile);
            } else if (APACHE_CONF.equals(language)) {
                color = context.getResources().getColor(R.color.lang_apacheconf);
            } else if (CSHARP.equals(language)) {
                color = context.getResources().getColor(R.color.lang_csharp);
            } else if (JUPYTER_NOTEBOOK.equals(language)) {
                color = context.getResources().getColor(R.color.lang_jupyternotebook);
            } else if (EMACS_LISP.equals(language)) {
                color = context.getResources().getColor(R.color.lang_emacslisp);
            } else if (TYPE_SCRIPT.equals(language)) {
                color = context.getResources().getColor(R.color.lang_typescript);
            } else if (CMAKE.equals(language)) {
                color = context.getResources().getColor(R.color.lang_cmake);
            } else if (D.equals(language)) {
                color = context.getResources().getColor(R.color.lang_d);
            } else if (RACKET.equals(language)) {
                color = context.getResources().getColor(R.color.lang_racket);
            } else if (ERLANG.equals(language)) {
                color = context.getResources().getColor(R.color.lang_erlang);
            } else if (SWIFT.equals(language)) {
                color = context.getResources().getColor(R.color.lang_swift);
            } else if (EAGLE.equals(language)) {
                color = context.getResources().getColor(R.color.lang_eagle);
            } else if (CLOJURE.equals(language)) {
                color = context.getResources().getColor(R.color.lang_clojure);
            } else if (HASKELL.equals(language)) {
                color = context.getResources().getColor(R.color.lang_haskell);
            } else if (LUA.equals(language)) {
                color = context.getResources().getColor(R.color.lang_lua);
            } else if (TEX.equals(language)) {
                color = context.getResources().getColor(R.color.lang_tex);
            } else if (RUBY.equals(language)) {
                color = context.getResources().getColor(R.color.lang_ruby);
            } else if (OBJECTIVE_C.equals(language)) {
                color = context.getResources().getColor(R.color.lang_objc);
            } else if (CSS.equals(language)) {
                color = context.getResources().getColor(R.color.lang_css);
            } else if (PHP.equals(language)) {
                color = context.getResources().getColor(R.color.lang_php);
            } else if (PERL.equals(language)) {
                color = context.getResources().getColor(R.color.lang_perl);
            } else if (STANDARDML.equals(language)) {
                color = context.getResources().getColor(R.color.lang_standardml);
            } else if (OCAML.equals(language)) {
                color = context.getResources().getColor(R.color.lang_ocaml);
            } else if (GROOVY.equals(language)) {
                color = context.getResources().getColor(R.color.lang_groovy);
            } else if (KOTLIN.equals(language)) {
                color = context.getResources().getColor(R.color.lang_kotlin);
            } else if (SCALA.equals(language)) {
                color = context.getResources().getColor(R.color.lang_scala);
            }
        }
        return color;
    }

}
