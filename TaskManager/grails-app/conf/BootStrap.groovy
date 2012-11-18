import com.taskmanager.Role
import com.taskmanager.User
import com.taskmanager.UserRole

class BootStrap {

    def init = { 
	def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(
			authority: 'ROLE_ADMIN', name: 'Admin').save(failOnError: true)
	def developerRole = Role.findByAuthority('ROLE_DEVELOPER') ?: new Role(
		authority: 'ROLE_DEVELOPER', name: 'Developer').save(failOnError: true)
	def adminUser = User.findByUsername('admin') ?: new User(
			username: 'admin',
			password: 'admin',
			enabled: true,
			firstName: 'Maciej',
			lastName: 'Czajkowski',
			email: 'kuchar90@gmail.com').save(failOnError: true)
			if (!adminUser.authorities.contains(adminRole)) {
				UserRole.create adminUser, adminRole
			}
    }
    def destroy = {
    }
}
