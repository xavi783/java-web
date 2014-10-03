package database;

import general.BaseTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uk.login.LoginDetail;
import com.uk.login.dao.LoginDetailsDao;
import com.uk.login.utils.ListHeaders;
import com.uk.login.utils.RolesList;
import com.uk.login.utils.UsersList;
import com.uk.login.utils.RolesList.RoleListDataSource;

public class UsersRolesTest extends BaseTest{
	
	@Autowired LoginDetailsDao loginDetailsDao;
	@Autowired UsersList userList;
	@Autowired RolesList rolesList;
	private List<RoleListDataSource> datasource = new ArrayList<RoleListDataSource>();
	
	@Test
	@Ignore //passed
	@SuppressWarnings("unchecked")
	public void getAllRoles(){
		List<LoginDetail> list = (List<LoginDetail>)loginDetailsDao.getAllRoles();
		for(LoginDetail l : list){
			System.out.println(l);
		}
	}
	
	@Test
	@Ignore //passed
	@SuppressWarnings("unchecked")
	public void rolesBuildDataSource(){
		RolesList l = new RolesList();
		RolesList.RoleListDataSource r;
		List<LoginDetail> allRoles = (List<LoginDetail>)loginDetailsDao.getAllRoles();		
		for(LoginDetail a : allRoles){
			r = l.new RoleListDataSource(a.getAuthority(),a.getSuccessURL(),Integer.toString(a.getPriority()));
			datasource.add(r);
		}
		for(RoleListDataSource d : datasource){
			System.out.println(d);
		}
	}
	
	@Test
	@Ignore
	public void listUsers(){
		userList.buildHeaders().buildDataSource();
		List<ListHeaders> listHeaders = userList.getColumns();
		List<UsersList.UserListDataSource> usersDatasource = userList.getDatasource();
		for(ListHeaders l : listHeaders){
			System.out.println(l);
		}
		for(UsersList.UserListDataSource u : usersDatasource){
			System.out.println(u);
		}		
	}
	
	@Test
	@Ignore
	public void listRoles(){
		rolesList.buildHeaders().buildDataSource();
		List<ListHeaders> listHeaders = rolesList.getColumns();
		List<RolesList.RoleListDataSource> rolesDatasource = rolesList.getDatasource();
		for(ListHeaders l : listHeaders){
			System.out.println(l);
		}
		for(RolesList.RoleListDataSource r : rolesDatasource){
			System.out.println(r);
		}
	}

}
