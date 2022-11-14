package bank.dao.transaction_management;

import bank.entity.transaction_management.TransactionsEntity;
import bank.repository.transaction_management.TransactionsRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TransactionsDao implements TransactionsRepository {
    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<TransactionsEntity> findByTransactionDate(String transactionDate) {
        List<TransactionsEntity> transactionsEntityList = manager.createNamedQuery("TransactionsEntity.findByTransactionDate", TransactionsEntity.class)
                .setParameter(1, transactionDate)
                .getResultList();
        return transactionsEntityList;
    }

    @Override
    public List<TransactionsEntity> findAll() {
        return null;
    }

    @Override
    public List<TransactionsEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TransactionsEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TransactionsEntity> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TransactionsEntity transactionsEntity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TransactionsEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TransactionsEntity> S save(S s) {
        return null;
    }

    @Override
    public <S extends TransactionsEntity> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<TransactionsEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TransactionsEntity> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<TransactionsEntity> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TransactionsEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends TransactionsEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TransactionsEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TransactionsEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TransactionsEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TransactionsEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TransactionsEntity> boolean exists(Example<S> example) {
        return false;
    }
}
