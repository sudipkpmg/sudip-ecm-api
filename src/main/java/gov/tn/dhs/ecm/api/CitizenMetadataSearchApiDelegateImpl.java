package gov.tn.dhs.ecm.api;

import com.box.sdk.*;
import gov.tn.dhs.ecm.AppProperties;
import gov.tn.dhs.ecm.model.CitizenMetadataQuery;
import gov.tn.dhs.ecm.model.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenMetadataSearchApiDelegateImpl implements CitizenMetadataSearchApiDelegate {

    private static Logger logger = LoggerFactory.getLogger(CitizenMetadataSearchApiDelegateImpl.class);

    @Autowired
    private AppProperties appProperties;

    @Override
    public ResponseEntity<List<FileInfo>> citizenMetadataSearchPost(CitizenMetadataQuery citizenMetadataQuery) {
        logger.info(citizenMetadataQuery.toString());
        try {
            BoxAPIConnection api = new BoxAPIConnection(appProperties.getDeveloperToken());

            String rootFolderId = BoxFolder.getRootFolder(api).getID();
            logger.info("rootFolderId = {}", rootFolderId);

            long offsetValue = 0;
            long limitValue = 10;
            BoxSearch boxSearch = new BoxSearch(api);
            BoxSearchParameters searchParameters = new BoxSearchParameters();
//            BoxMetadataFilter metadataFilter = new BoxMetadataFilter();
//            metadataFilter.setScope("global");
//            metadataFilter.setTemplateKey("properties");
//            metadataFilter.addFilter("/FirstName", "John");
//            searchParameters.setMetadataFilter(metadataFilter);
            searchParameters.setQuery("707349658850");
            searchParameters.setType("folder");
//            List<String> folderIds = Lists.newArrayList(rootFolderId);
//            searchParameters.setAncestorFolderIds(folderIds);

            PartialCollection<BoxItem.Info> searchResults = boxSearch.searchRange(offsetValue, limitValue, searchParameters);

            for (BoxItem.Info info : searchResults) {
                String id = info.getID();
                logger.info(id);
                BoxFolder boxFolder = new BoxFolder(api, id);
                Metadata metadata = boxFolder.getMetadata();
                logger.info(metadata.toString());
            }

//            String from = "global.properties";
//            String query = "FirstName = :firstName";
////            String ancestorFolderId = "120822686345";
//            String ancestorFolderId = "0";
//            JsonObject queryParameters = new JsonObject().add("firstName", "John");
////            JsonArray orderBy = new JsonArray();
////            JsonObject primaryOrderBy = new JsonObject().add("FirstName", "primarySortKey").add("direction", "asc");
////            JsonObject secondaryOrderBy = new JsonObject().add("LastName", "secondarySortKey").add("direction", "asc");
////            orderBy.add(primaryOrderBy).add(secondaryOrderBy);

//            BoxResourceIterable<BoxMetadataQueryItem> results = MetadataTemplate.executeMetadataQuery(api, from, query, queryParameters, ancestorFolderId, null, orderBy);
//            BoxResourceIterable<BoxMetadataQueryItem> results = MetadataTemplate.executeMetadataQuery(api, from, query, queryParameters, ancestorFolderId);
//            for (BoxMetadataQueryItem r: results) {
//                String customFieldValue = r.getMetadata().get("global").get(0).get("/FirstName");
//                System.out.println(customFieldValue);
//            }

        } catch (Exception ex) {
            return new ResponseEntity(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity(null, HttpStatus.CONFLICT);
    }
}
