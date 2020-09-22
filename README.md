# shadow-cljs-clara-rules

Reproduces a problem when using [clara-rules](http://www.clara-rules.org/) with shadow-cljs.
Recompiling project more than once results in a warning for every function which is declared in the same namespace as clara rules are declared.

## Reproduce

```bash
yarn watch
yarn offline
```

uncomment line 22:

```clojure
(defrule is-cold-and-windy-map
  [:temp [{degrees :degrees}] (< degrees 20) (== ?t degrees)]

  =>

  (prn "@@@ notifs/is-cold-and-windy-map")
  ;; uncommenting results in a warning `Use of undeclared Var app.core/fu`
  (fu))
```

Force recompilation, e.g. in app.core namespace:

```
M+x touch
```
