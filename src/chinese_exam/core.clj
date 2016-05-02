(ns chinese-exam.core)

(def xmas (map #(.trim %) (.split (slurp "resources/xmas.txt") "\n")))
(def genesis (map #(.trim %) (.split (slurp "resources/genesis.txt") "\n")))

(defn conj-concat [m k v]
  (if (m k)
    (update-in m [k] conj v)
    (assoc m k [v])))

(defn group-all [lines]
  (reduce (fn [m line]
            (reduce (fn [m char]
                      (conj-concat m char line))
                    m line))
            {} (distinct lines)))

(defn spit-groups [f lines]
  (spit f
        (apply str
               (interpose "\n"
                          (map (fn [[k v]] (str k " " v)) (group-all lines))))))

(spit-groups "resources/xmas2.txt" xmas)

(defn count-f [f]
  (let [
        forms (map #(read-string (.substring % 2)) (.split (.trim (slurp f)) "\n"))
        ]
    (count (apply str (flatten forms)))))

(count-f "resources/xmas2.txt")

(spit-groups "resources/genesis2.txt" genesis)
(count-f "resources/genesis2.txt")
