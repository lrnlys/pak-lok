package com.rinanzhi.springbootseed.service;

import com.rinanzhi.springbootseed.entity.BaseEntity;
import com.rinanzhi.springbootseed.util.validate.Default;
import com.rinanzhi.springbootseed.util.validate.Insert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.rinanzhi.springbootseed.util.BeanUtils.getNullPropertyNames;

public class CrudService<ENTITY extends BaseEntity<ID>, ID,
        REPOSITORY extends JpaRepository<ENTITY, ID> & JpaSpecificationExecutor<ENTITY>> implements
        CrudRepository<ENTITY, ID>, QueryByExampleExecutor<ENTITY>, JpaSpecificationExecutor<ENTITY> {

    @Autowired
    protected REPOSITORY repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <S extends ENTITY> S save(S entity) {
        S target;
        ID id = entity.getPrimaryKey();
        if (id != null) {//update
            target = (S) this.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find update target from database"));
            BeanUtils.copyProperties(entity, target, getNullPropertyNames(entity));
            return (S) this.update(entity);
        } else {//insert
            target = entity;
            return (S) this.insert(target);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Validated(Insert.class)
    public <S extends ENTITY> S insert(@Valid S entity) {
        return repository.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Validated(Default.class)
    public <S extends ENTITY> S update(@Valid S entity) {
        S target = (S) this.findById(entity.getPrimaryKey()).orElseThrow(() -> new NoSuchElementException("Can't find update target from database"));
        BeanUtils.copyProperties(entity, target, getNullPropertyNames(entity));
        return repository.save(entity);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <S extends ENTITY> Iterable<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ENTITY> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ENTITY> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ENTITY> findAllById(Iterable<ID> iterable) {
        return repository.findAllById(iterable);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(ENTITY ENTITY) {
        repository.delete(ENTITY);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(Iterable<? extends ENTITY> iterable) {
        repository.deleteAll(iterable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public <S extends ENTITY> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    @Transactional(readOnly = true)
    public <S extends ENTITY> Iterable<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    @Transactional(readOnly = true)
    public <S extends ENTITY> Iterable<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public <S extends ENTITY> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends ENTITY> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends ENTITY> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ENTITY> findOne(Specification<ENTITY> specification) {
        return repository.findOne(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAll(Specification<ENTITY> specification) {
        return repository.findAll(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ENTITY> findAll(Specification<ENTITY> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAll(Specification<ENTITY> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Specification<ENTITY> specification) {
        return repository.count(specification);
    }

}
