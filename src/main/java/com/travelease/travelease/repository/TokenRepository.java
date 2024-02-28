// package com.travelease.travelease.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// import com.travelease.travelease.model.sessionmodel.AdminSession;



// public interface TokenRepository extends JpaRepository<AdminSession, String> {
//     @Query(nativeQuery = true, value ="SELECT * FROM session_token e WHERE e.email_id = :emailAddress")
//     AdminSession presentEmail(@Param("emailAddress") String emailAddress);

//     @Query(nativeQuery = true, value ="SELECT * FROM session_token e WHERE e.token_id = :tokenId")
//     AdminSession presentToken(@Param("tokenId")String token);
// }
