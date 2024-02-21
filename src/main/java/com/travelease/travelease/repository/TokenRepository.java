package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.tokenmodel.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Query(nativeQuery = true, value ="SELECT * FROM session_token e WHERE e.email_id = :emailAddress")
    Token presentEmail(@Param("emailAddress") String emailAddress);

    @Query(nativeQuery = true, value ="SELECT * FROM session_token e WHERE e.token_id = :tokenId")
    Token presentToken(@Param("tokenId")String token);
}
