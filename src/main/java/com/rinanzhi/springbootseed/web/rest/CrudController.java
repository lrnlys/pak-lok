package com.rinanzhi.springbootseed.web.rest;

import com.rinanzhi.springbootseed.domain.web.FindAllRequest;
import com.rinanzhi.springbootseed.entity.BaseEntity;
import com.rinanzhi.springbootseed.service.CrudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.springframework.util.StringUtils.isEmpty;

@Log4j2
public class CrudController<ENTITY extends BaseEntity<ID>, ID, SERVICE extends CrudService<ENTITY, ID, ?>> implements CrudRepository<ENTITY, ID> {

    @Autowired
    protected SERVICE service;

    @PostMapping(value = "/search")
    public Iterable<ENTITY> findAll(@RequestBody(required = false) FindAllRequest<ENTITY> findAllRequest) {
        try {
            if (findAllRequest == null || (isEmpty(findAllRequest.getConditions()) && isEmpty(findAllRequest.getPageable()))) {
                return service.findAll();
            } else if (findAllRequest.getPageable() != null) {
                return this.findAll(Example.of(findAllRequest.getConditions()), findAllRequest.getPageable());
            } else {
                return this.findAll(Example.of(findAllRequest.getConditions()));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getMessage(e));
        }
    }

    protected Iterable<ENTITY> findAll(Example<ENTITY> example, Pageable pageable) {
        return service.findAll(example, pageable);
    }

    protected Iterable<ENTITY> findAll(Example<ENTITY> example) {
        return service.findAll(example);
    }

    @Override
    @PostMapping
    public <S extends ENTITY> S save(@RequestBody S s) {
        try {
            ID primaryKey = s.getPrimaryKey();
            if (primaryKey != null) {
                return service.update(s);
            } else {
                return service.insert(s);
            }
//            return service.save(s);
        } catch (Exception e) {
            log.error("save : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Save failed");
        }
    }

    @Override
    @PostMapping("/all")
    public <S extends ENTITY> Iterable<S> saveAll(@RequestBody Iterable<S> iterable) {
        try {
            return service.saveAll(iterable);
        } catch (Exception e) {
            log.error("saveAll : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Save all failed");
        }
    }

    @Override
    public Optional<ENTITY> findById(ID id) {
        try {
            return service.findById(id);
        } catch (Exception e) {
            log.error("findById : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Find by id failed");
        }
    }

    @GetMapping("/{id}")
    public ENTITY findByIdOrNull(@PathVariable("id") ID id) {
        Optional<ENTITY> entityOptional = findById(id);
        return entityOptional.orElse(null);
//        throw new UnsupportedOperationException();
    }

    @Override
    @GetMapping("/{id}/exist")
    public boolean existsById(@PathVariable("id") ID id) {
        try {
            return service.existsById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getMessage(e));
        }
    }

    @Override
    @GetMapping("/allInIds")
    public Iterable<ENTITY> findAllById(@RequestBody Iterable<ID> iterable) {
        try {
            return service.findAllById(iterable);
        } catch (Exception e) {
            log.error("findAllById : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Find all by id failed");
        }
    }

    @Override
    @GetMapping("/count")
    public long count() {
        try {
            return service.count();
        } catch (Exception e) {
            log.error("count : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Count record failed");
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") ID id) {
        log.info("Going to delete record with primary key {}", id);
        try {
            service.deleteById(id);
        } catch (Exception e) {
            log.error("delete : ", e);
        }
    }

    @Override
    public Iterable<ENTITY> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(@RequestBody ENTITY entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(@RequestBody Iterable<? extends ENTITY> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
