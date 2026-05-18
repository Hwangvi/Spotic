package com.backSpotic.backSpotic.repo;
import com.backSpotic.backSpotic.model.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRepository extends JpaRepository<Ip,Long> {
}
