package simdev.demo.services;

import simdev.demo.models.Tasks;

import java.util.List;

/**
 * Service dédié à la récupération (lecture seule) des données liées aux tâches.
 * 
 * Cette interface suit le principe de séparation des responsabilités (SRP) en isolant
 * les opérations de lecture, afin de permettre une meilleure testabilité, une
 * potentielle optimisation par cache ou moteur de recherche (ElasticSearch, Redis),
 * et une architecture orientée CQRS.
 */
public interface TasksGetOneService {

    /**
     * Récupère une tâche à partir de son identifiant unique.
     *
     * @param id Identifiant de la tâche
     * @return L'entité {@link Tasks} correspondante
     * @throws simdev.demo.exceptions.TaskNotFoundException si aucune tâche n'est trouvée
     */
    Tasks getTaskById(Long id);

}
