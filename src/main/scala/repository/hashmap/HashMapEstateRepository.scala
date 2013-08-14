package repository.hashmap

import model.estate.Estate
import repository.EstateRepository

class HashMapEstateRepository extends HashMapRepository[Estate[Any]] with EstateRepository